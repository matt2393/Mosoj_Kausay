package com.gotasoft.mosojkausay.view.momentos_magicos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.FragmentMmBinding
import com.gotasoft.mosojkausay.utils.getToken
import com.gotasoft.mosojkausay.view.momentos_magicos.add_mm.AddMMFragment
import com.gotasoft.mosojkausay.view.momentos_magicos.fotos_mm.FotosMMFragment
import com.gotasoft.mosojkausay.view.momentos_magicos.testimonio_ad_mm.TestAdMMFragment
import kotlinx.coroutines.flow.collect

class MMFragment: Fragment() {
    private val viewModel: MMViewModel by viewModels()
    companion object {
        val TAG = MMFragment::class.java.name
    }
    private var token = ""
    private var adapter: MMAdapter? = null
    private var binding: FragmentMmBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMmBinding.inflate(inflater, container, false)

        requireActivity().title = "Momentos Mágicos"
        adapter = MMAdapter(clickTestAd = {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.containerMM, TestAdMMFragment.newInstance(it.id), TestAdMMFragment.TAG)
                .addToBackStack(TestAdMMFragment.TAG)
                .commit()
        }, clickFotos = {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.containerMM, FotosMMFragment.newInstance(it.id), FotosMMFragment.TAG)
                .addToBackStack(FotosMMFragment.TAG)
                .commit()
        })
        with(binding!!) {
            recyclerMM.layoutManager = LinearLayoutManager(requireContext())
            recyclerMM.adapter = adapter
            fabAddMM.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.containerMM, AddMMFragment(), AddMMFragment.TAG)
                    .addToBackStack(AddMMFragment.TAG)
                    .commit()
            }

            swipeMM.setOnRefreshListener {
                if(token.isNotEmpty()) {
                    viewModel.getMM(token)
                }
            }
        }

        flowScopes()
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        if(token.isNotEmpty()) {
            viewModel.getMM(token)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    token = "Bearer $it"
                    viewModel.getMM(token)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.mm.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arrayMM = it.data
                        adapter?.notifyDataSetChanged()
                        binding?.swipeMM?.isRefreshing = false
                    }
                    is StateData.Error -> {
                        binding?.swipeMM?.isRefreshing = false
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