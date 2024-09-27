package com.gotasoft.mosojkausay_mobile.view.mis_seguimientos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay_mobile.R

class MisSegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_seg)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerMisSeg, MisSegFragment(), MisSegFragment.TAG)
            .commit()
    }
}