package com.gotasoft.mosojkausay.view.noticia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.ActivityNoticiaBinding
import com.gotasoft.mosojkausay.view.noticia.list.NoticiaAdapter
import com.gotasoft.mosojkausay.view.noticia.list.NotificaListFragment
import kotlinx.coroutines.flow.collect

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