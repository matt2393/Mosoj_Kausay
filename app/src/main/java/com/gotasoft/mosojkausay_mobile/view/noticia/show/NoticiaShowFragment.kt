package com.gotasoft.mosojkausay_mobile.view.noticia.show

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.URL_DOWNLOAD_FILE
import com.gotasoft.mosojkausay_mobile.databinding.FragmentNoticiaShowBinding
import com.gotasoft.mosojkausay_mobile.view.noticia.NoticiaViewModel

class NoticiaShowFragment: Fragment() {
    private val viewModel: NoticiaViewModel by activityViewModels()
    private var adapter: NotShowAdapter? = null
    private var binding: FragmentNoticiaShowBinding? = null
    companion object {
        val TAG = NoticiaShowFragment::class.java.simpleName
        private const val NOT_ID = "NOT_ID"
        fun newInstance(id: Int) = NoticiaShowFragment().apply {
            arguments = Bundle().apply {
                putInt(NOT_ID, id)
            }
        }
    }
    private var idNot = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            idNot = it.getInt(NOT_ID,0)
        }
        binding = FragmentNoticiaShowBinding.inflate(inflater, container, false)

        adapter = NotShowAdapter {
            when(it.multimediaTipo) {
                "video" -> {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(it.enlace))
                    )
                }
                "documento" -> startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("$URL_DOWNLOAD_FILE${it.id}"))
                )
                "imagen" -> {}
                "enlace" -> {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(it.enlace))
                    )
                }
            }
        }
        binding?.recyclerNotShow?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerNotShow?.adapter = adapter
        flowScopes()
        viewModel.showNoticia(idNot)
        return binding?.root
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            viewModel.notShow.collect {
                when(it) {
                    is StateData.Success -> {
                        if(it.data.multimediaList!=null) {
                            adapter?.arrayMulti = ArrayList(it.data.multimediaList!!)
                            adapter?.notifyDataSetChanged()
                        }
                        binding?.textTitNotShow?.text = it.data.noticia?.titulo
                        binding?.textContNotShow?.text = it.data.noticia?.contenido
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