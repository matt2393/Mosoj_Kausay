package com.gotasoft.mosojkausay.view.noticia.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.FragmentNoticiaListBinding
import com.gotasoft.mosojkausay.view.noticia.NoticiaViewModel
import com.gotasoft.mosojkausay.view.noticia.show.NoticiaShowFragment
import kotlinx.coroutines.flow.collect

class NotificaListFragment: Fragment() {
    private val viewModel: NoticiaViewModel by activityViewModels()
    private var adapter: NoticiaAdapter? = null
    private var binding: FragmentNoticiaListBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoticiaListBinding.inflate(inflater, container, false)
        adapter = NoticiaAdapter {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerNoticia, NoticiaShowFragment.newInstance(it.id), NoticiaShowFragment.TAG)
                .addToBackStack(NoticiaShowFragment.TAG)
                .commit()
        }
        requireActivity().title = "Noticias"
        with(binding!!) {
            recyclerNoticias.layoutManager = LinearLayoutManager(requireContext())
            recyclerNoticias.adapter = adapter

            swipeNoticias.setOnRefreshListener {
                viewModel.getNoticias()
            }
        }

        flowScopes()
        viewModel.getNoticias()

        return binding?.root
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            viewModel.noticias.collect {
                when(it) {
                    is StateData.Success -> {
                        binding?.swipeNoticias?.isRefreshing = false
                        adapter?.arrayNoticia = it.data
                        adapter?.notifyDataSetChanged()
                    }
                    is StateData.Error -> {
                        binding?.swipeNoticias?.isRefreshing = false
                    }
                    StateData.Loading -> {

                    }
                    StateData.None -> { }
                }
            }
        }
    }
}