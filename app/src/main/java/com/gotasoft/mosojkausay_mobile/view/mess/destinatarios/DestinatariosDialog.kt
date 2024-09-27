package com.gotasoft.mosojkausay_mobile.view.mess.destinatarios

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.databinding.DialogDestinatariosBinding
import com.gotasoft.mosojkausay_mobile.utils.getToken

class DestinatariosDialog: DialogFragment() {

    private val viewModel: DestViewModel by viewModels()
    private var adapter: DestAdapter? = null
    private var token: String = ""
    private var messId: Int  = 0
    companion object {
        val TAG = DestinatariosDialog::class.java.name
        private const val MESS_ID = "MessId"
        fun newInstance(messId: Int) = DestinatariosDialog().apply {
            arguments = Bundle().apply {
                putInt(MESS_ID, messId)
            }
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alert = AlertDialog.Builder(requireContext())
        val binding = DialogDestinatariosBinding.inflate(requireActivity().layoutInflater, null, false)
        alert.setView(binding.root)

        arguments?.let {
            messId = it.getInt(MESS_ID, 0)
        }

        adapter = DestAdapter()
        binding.recyclerDestinatarios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerDestinatarios.adapter = adapter
        binding.recyclerDestinatarios.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        alert.setTitle("Destinatarios")
        alert.setPositiveButton("Cerrar") { d, _ ->
            dismiss()
        }
        flowScopes()
        return alert.create()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    token = "Bearer $it"
                    viewModel.getDest(token, messId)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.dests.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arrayDest = it.data
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