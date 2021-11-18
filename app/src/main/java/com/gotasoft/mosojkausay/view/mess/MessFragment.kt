package com.gotasoft.mosojkausay.view.mess

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.TOKEN
import com.gotasoft.mosojkausay.databinding.FragmentMessBinding
import com.gotasoft.mosojkausay.view.mess.crear_mess.CrearMessActivity
import kotlinx.coroutines.flow.collect

class MessFragment: Fragment() {
    private val viewModel: MessViewModel by viewModels()
    private var adapter: MessAdapter? = null
    companion object {
        val TAG = MessFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentMessBinding.inflate(inflater, container, false)
        adapter = MessAdapter()
        bind.recyclerMess.layoutManager = LinearLayoutManager(requireContext())
        bind.recyclerMess.adapter = adapter
        bind.fabCrearMess.setOnClickListener {
            startActivity(Intent(requireContext(), CrearMessActivity::class.java))
        }

        flowScopes()
        viewModel.getMess(token = TOKEN)
        return bind.root
    }

    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            viewModel.mess.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arrayMess = it.data
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