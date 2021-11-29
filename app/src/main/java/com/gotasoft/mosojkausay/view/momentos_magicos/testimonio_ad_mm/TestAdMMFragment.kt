package com.gotasoft.mosojkausay.view.momentos_magicos.testimonio_ad_mm

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
import com.gotasoft.mosojkausay.databinding.FragmentTestimoniosMmBinding
import com.gotasoft.mosojkausay.utils.getToken
import com.gotasoft.mosojkausay.view.momentos_magicos.testimonio_ad_mm.test_ad_add_mm.TestAdMMAddFragment
import kotlinx.coroutines.flow.collect

class TestAdMMFragment: Fragment() {

    private val viewModel: TestAdMMViewModel by viewModels()
    companion object {
        val TAG = TestAdMMFragment::class.java.name
        private const val MM_ID = "MMID"
        fun newInstance(mmId: Int) = TestAdMMFragment().apply {
            arguments = Bundle().apply {
                putInt(MM_ID, mmId)
            }
        }
    }

    private var token = ""
    private var mm_id = 0
    private var adapter: TestAdMMAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTestimoniosMmBinding.inflate(inflater, container, false)

        requireActivity().title = "Testimonios Adicionales"
        arguments?.let {
            mm_id = it.getInt(MM_ID, 0)
        }
        adapter = TestAdMMAdapter()
        with(binding) {
            recyclerTesMM.layoutManager = LinearLayoutManager(requireContext())
            recyclerTesMM.adapter = adapter
            fabAddTesMM.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.containerMM, TestAdMMAddFragment.newInstance(mm_id), TestAdMMAddFragment.TAG)
                    .addToBackStack(TestAdMMAddFragment.TAG)
                    .commit()
            }
        }

        flowScopes()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(token.isNotEmpty()) {
            viewModel.getTestAd(token, mm_id)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    token = "Bearer $it"
                    viewModel.getTestAd(token, mm_id)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.testAd.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arrayTes = it.data
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