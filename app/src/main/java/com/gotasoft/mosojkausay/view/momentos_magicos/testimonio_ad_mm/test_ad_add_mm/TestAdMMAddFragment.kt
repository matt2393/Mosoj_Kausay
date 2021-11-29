package com.gotasoft.mosojkausay.view.momentos_magicos.testimonio_ad_mm.test_ad_add_mm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.FragmentAddTesMmBinding
import com.gotasoft.mosojkausay.model.entities.request.TestimonioAdRequest
import com.gotasoft.mosojkausay.utils.getToken
import kotlinx.coroutines.flow.collect

class TestAdMMAddFragment: Fragment() {
    private val viewModel: TestAdMMAddViewModel by viewModels()
    companion object {
        val TAG = TestAdMMAddFragment::class.java.name
        private const val MM_ID = "MMID"
        fun newInstance(mmId: Int) = TestAdMMAddFragment().apply {
            arguments = Bundle().apply {
                putInt(MM_ID, mmId)
            }
        }
    }

    private var token = ""
    private var mm_id = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddTesMmBinding.inflate(inflater, container, false)

        arguments?.let {
            mm_id = it.getInt(MM_ID, 0)
        }
        with(binding) {
            buttonGuardarTesMM.setOnClickListener {
                val pp = editPreguntaAddTesMM.text.toString()
                val tes = editTesAddTesMM.text.toString()
                if(pp.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba una pregunta", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(tes.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba el testimonio", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val tesAddRequest = TestimonioAdRequest()
                tesAddRequest.momento_magico_id = mm_id
                tesAddRequest.pregunta = pp
                tesAddRequest.testimonio = tes
                viewModel.addTestAd(token, tesAddRequest)
            }
        }
        flowScopes()
        return binding.root
    }

    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    token = "Bearer $it"
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.testAd.collect {
                when(it) {
                    is StateData.Success -> {
                        Toast.makeText(requireContext(), "Se guardo con éxito", Toast.LENGTH_SHORT).show()
                        requireActivity().onBackPressed()
                    }
                    is StateData.Error -> {
                        Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
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