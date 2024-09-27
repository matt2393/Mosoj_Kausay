package com.gotasoft.mosojkausay_mobile.view.seguimiento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay_mobile.R

class SeguimientoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seguimiento)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerSeg, SeguimientoFragment(), SeguimientoFragment.TAG)
            .commit()
    }
}