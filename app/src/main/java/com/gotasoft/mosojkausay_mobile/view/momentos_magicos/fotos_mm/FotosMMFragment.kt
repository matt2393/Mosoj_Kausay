package com.gotasoft.mosojkausay_mobile.view.momentos_magicos.fotos_mm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.databinding.FragmentFotosMmBinding
import com.gotasoft.mosojkausay_mobile.utils.getToken
import com.gotasoft.mosojkausay_mobile.view.momentos_magicos.fotos_mm.foto_mm_add.FotoMMAddFragment

class FotosMMFragment: Fragment() {
    private val viewModel: FotosMMViewModel by viewModels()
    companion object {
        val TAG = FotosMMFragment::class.java.name
        private const val MM_ID = "MMID"
        fun newInstance(mmId: Int) = FotosMMFragment().apply {
            arguments = Bundle().apply {
                putInt(MM_ID, mmId)
            }
        }
    }

    private var token = ""
    private var mm_id = 0
    private var adapter: FotosMMAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFotosMmBinding.inflate(inflater, container, false)
        requireActivity().title = "Fotografias"
        arguments?.let {
            mm_id = it.getInt(MM_ID, 0)
            adapter = FotosMMAdapter()
            with(binding) {
                recyclerFotosMM.layoutManager = LinearLayoutManager(requireContext())
                recyclerFotosMM.adapter = adapter
                fabAddFotosMM.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.containerMM, FotoMMAddFragment.newInstance(mm_id), FotoMMAddFragment.TAG)
                        .addToBackStack(FotoMMAddFragment.TAG)
                        .commit()
                }
            }
        }
        flowScopes()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(token.isNotEmpty()) {
            viewModel.getFotos(token, mm_id)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    token = "Bearer $it"
                    viewModel.getFotos(token, mm_id)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.fotos.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arrayFotos = it.data
                        adapter?.notifyDataSetChanged()
                    }
                    is StateData.Error -> {

                    }
                    StateData.Loading -> {

                    }
                    StateData.None -> {

                    }
                }
            }
        }
    }
}