package com.gotasoft.mosojkausay.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.ActivityLoginBinding
import com.gotasoft.mosojkausay.utils.decodeJWT
import com.gotasoft.mosojkausay.view.login.init.InitFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerLogin, InitFragment(), InitFragment.TAG)
            .commit()
        //val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IjcyNzUwNDciLCJyb2wiOiJhZG1pbmlzdHJhZG9yIiwiaWF0IjoxNjM1MzY2MDM1fQ.cSacjgovPG3VKaQ4YJN8s3BJK2HdIJBf8zJNfKbj41k"
        //Log.e("JWT", jwt.decodeJWT().toString())
    }
}