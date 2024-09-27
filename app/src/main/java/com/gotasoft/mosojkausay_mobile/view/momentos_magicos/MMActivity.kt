package com.gotasoft.mosojkausay_mobile.view.momentos_magicos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay_mobile.R

class MMActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mm)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerMM, MMFragment(), MMFragment.TAG)
            .commit()
    }
}