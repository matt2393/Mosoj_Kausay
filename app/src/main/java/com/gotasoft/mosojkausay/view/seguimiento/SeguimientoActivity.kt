package com.gotasoft.mosojkausay.view.seguimiento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay.R

class SeguimientoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seguimiento)
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerSeg, SeguimientoFragment(), SeguimientoFragment.TAG)
            .commit()
    }
}