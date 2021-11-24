package com.gotasoft.mosojkausay.view.corres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay.R

class CorresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corres)
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerCorres, CorresFragment(), CorresFragment.TAG)
            .commit()
    }
}