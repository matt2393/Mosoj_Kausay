package com.gotasoft.mosojkausay.view.mess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.ActivityMessBinding

class MessActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMessBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerMess, MessFragment(), MessFragment.TAG)
            .commit()
        title = "Mensajeria"

    }
}