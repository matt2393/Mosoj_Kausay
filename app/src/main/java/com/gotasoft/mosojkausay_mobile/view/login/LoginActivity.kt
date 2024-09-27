package com.gotasoft.mosojkausay_mobile.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay_mobile.*
import com.gotasoft.mosojkausay_mobile.databinding.ActivityLoginBinding
import com.gotasoft.mosojkausay_mobile.model.entities.request.FcmReq
import com.gotasoft.mosojkausay_mobile.utils.*
import com.gotasoft.mosojkausay_mobile.view.MessageDialog
import com.gotasoft.mosojkausay_mobile.view.home.HomeActivity
import com.gotasoft.mosojkausay_mobile.view.login.init.InitFragment

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var bind: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)


        viewModel.addContador()
        //val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IjcyNzUwNDciLCJyb2wiOiJhZG1pbmlzdHJhZG9yIiwiaWF0IjoxNjM1MzY2MDM1fQ.cSacjgovPG3VKaQ4YJN8s3BJK2HdIJBf8zJNfKbj41k"
        //Log.e("JWT", jwt.decodeJWT().toString())

        lifecycleScope.launchWhenStarted {
            getToken(this@LoginActivity).collect {
                if(it != null) {
                    Tools.getFCMToken { tokenFCM ->
                        if (tokenFCM.isNotEmpty()) {
                            viewModel.editFCM(
                                "Bearer $it", FcmReq(tokenFCM)
                            )
                        }
                        TOKEN = it
                        val dataToken = it.decodeJWT()
                        val tipoPersonal = if (dataToken.size > 1) {
                            val tokenR = dataToken[1].fromJsonToken()
                            when (tokenR.rol) {
                                US_ADMIN -> TipoPersonal.ADMIN
                                US_PATROCINIO, US_FACILITADOR, US_TECNICO -> TipoPersonal.TECNICO
                                else -> TipoPersonal.TECNICO
                            }
                        } else TipoPersonal.TECNICO
                        startActivity(
                            Intent(this@LoginActivity, HomeActivity::class.java)
                                .putExtra(HomeActivity.TIPO, TipoIngreso.PERSONAL.name)
                                .putExtra(HomeActivity.TIPO_PERSONAL, tipoPersonal.name)
                        )
                        finish()
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.contador.collect{
                when(it) {
                    is StateData.Success -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.containerLogin, InitFragment(), InitFragment.TAG)
                            .commit()
                    }
                    is StateData.Error -> {
                        Log.e("ErrorContador", it.error.toString())
                        MessageDialog.newInstance("Error", "Ocurrio un error al inicia", "Cerrar", {
                            finish()
                        }).show(supportFragmentManager, MessageDialog.TAG)
                        //Toast.makeText(this@LoginActivity, "Ocurrio un error al iniciar", Toast.LENGTH_SHORT).show()
                        //finish()
                    }
                    StateData.Loading -> {
                    }
                    StateData.None -> {
                    }
                }
            }
        }
    }
}