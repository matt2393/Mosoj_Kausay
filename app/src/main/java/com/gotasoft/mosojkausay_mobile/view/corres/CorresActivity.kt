package com.gotasoft.mosojkausay_mobile.view.corres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay_mobile.R

class CorresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corres)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerCorres, CorresFragment(), CorresFragment.TAG)
            .commit()
    }
}