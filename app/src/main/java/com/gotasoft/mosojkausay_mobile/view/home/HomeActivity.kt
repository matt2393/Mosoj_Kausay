package com.gotasoft.mosojkausay_mobile.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay_mobile.BuildConfig
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ActivityHomeBinding
import com.gotasoft.mosojkausay_mobile.utils.*
import com.gotasoft.mosojkausay_mobile.view.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var bind: ActivityHomeBinding
    companion object {
        const val TIPO = "Tipo"
        const val TIPO_PERSONAL = "TIPO_PERSONAL"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        var tipo = TipoIngreso.PUBLICO
        var tipoPersonal = TipoPersonal.TECNICO
        intent?.let {
            it.getStringExtra(TIPO)?.let { t ->
                tipo = when (t) {
                    TipoIngreso.PUBLICO.name -> TipoIngreso.PUBLICO
                    TipoIngreso.PERSONAL.name -> TipoIngreso.PERSONAL
                    else -> TipoIngreso.PUBLICO
                }
            }
            it.getStringExtra(TIPO_PERSONAL)?.let { t ->
                tipoPersonal = when (t) {
                    TipoPersonal.ADMIN.name -> TipoPersonal.ADMIN
                    TipoPersonal.TECNICO.name -> TipoPersonal.TECNICO
                    else -> TipoPersonal.TECNICO
                }
            }
        }
        when(tipo) {
            TipoIngreso.PUBLICO -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerHome, HomePublicFragment())
                    .commit()
            }
            TipoIngreso.PERSONAL -> {
                when(tipoPersonal) {
                    TipoPersonal.ADMIN -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.containerHome, HomeAdminFragment())
                            .commit()
                    }
                    TipoPersonal.TECNICO -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.containerHome, HomeTecnicoFragment())
                            .commit()
                    }
                    else -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.containerHome, HomePublicFragment())
                            .commit()
                    }
                }

            }
        }
        bind.textVersionHome.text = getString(R.string.version, BuildConfig.VERSION_NAME)
        flowScope()
    }

    private fun flowScope() {
        lifecycleScope.launchWhenStarted {
            getToken(this@HomeActivity).collect {
                if (it!=null) {
                    val tipo = it.tokenTipoUsApi()
                    bind.textTipoUsHome.text = tipo
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==  R.id.menuLogoutMain) {
            AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Esta seguro de cerrar sesión?")
                .setPositiveButton("Aceptar") { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        removeToken(this@HomeActivity)
                    }
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                    finish()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog?.dismiss()
                }
                .show()

        }
        return super.onOptionsItemSelected(item)
    }
}