package com.gotasoft.mosojkausay.view.noticia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.ActivityNoticiaBinding
import kotlinx.coroutines.flow.collect

class NoticiaActivity : AppCompatActivity() {
    private val viewModel: NoticiaViewModel by viewModels()
    private lateinit var binding: ActivityNoticiaBinding
    private var adapter: NoticiaAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticiaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = NoticiaAdapter()
        title = "Noticias"
        with(binding) {
            recyclerNoticias.layoutManager = LinearLayoutManager(this@NoticiaActivity)
            recyclerNoticias.adapter = adapter
        }

        flowScopes()
        viewModel.getNoticias()
    }

    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            viewModel.noticias.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arrayNoticia = it.data
                        adapter?.notifyItemRangeInserted(0, it.data.size)
                    }
                    is StateData.Error -> {

                    }
                    StateData.Loading -> {

                    }
                    StateData.None -> { }
                }
            }
        }
    }
}