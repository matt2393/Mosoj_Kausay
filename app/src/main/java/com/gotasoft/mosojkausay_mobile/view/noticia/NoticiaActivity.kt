package com.gotasoft.mosojkausay_mobile.view.noticia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ActivityNoticiaBinding
import com.gotasoft.mosojkausay_mobile.view.noticia.list.NotificaListFragment

class NoticiaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticiaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticiaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerNoticia, NotificaListFragment(), "NotList")
            .commit()
    }
}