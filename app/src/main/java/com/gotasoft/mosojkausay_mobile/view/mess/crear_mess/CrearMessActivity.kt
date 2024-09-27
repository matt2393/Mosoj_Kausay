package com.gotasoft.mosojkausay_mobile.view.mess.crear_mess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ActivityCrearMessBinding

class CrearMessActivity : AppCompatActivity() {
    private lateinit var bind: ActivityCrearMessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityCrearMessBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerCrearMess, CrearMess1Fragment(), CrearMess1Fragment.TAG)
            .commit()
    }
}