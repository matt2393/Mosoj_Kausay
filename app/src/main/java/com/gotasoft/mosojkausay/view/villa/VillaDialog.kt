package com.gotasoft.mosojkausay.view.villa

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.TODOS
import com.gotasoft.mosojkausay.databinding.DialogVillasBinding
import com.gotasoft.mosojkausay.model.entities.response.VillaResponse
import kotlinx.coroutines.flow.collect

class VillaDialog: DialogFragment() {

    private val viewModel: VillaViewModel by viewModels()
    private interface OnSelectDialog {
        fun onSelect(villa: VillaResponse)
    }

    companion object {
        val TAG = VillaDialog::class.java.name
        fun newInstance(select: (villa: VillaResponse) -> Unit) = VillaDialog().apply {
            onSelectDialog = object : OnSelectDialog {
                override fun onSelect(villa: VillaResponse) {
                    select(villa)
                }
            }
        }
    }
    private var onSelectDialog: OnSelectDialog? = null
    private var adapter: VillaAdapter? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alert = AlertDialog.Builder(requireContext())
        val binding = DialogVillasBinding.inflate(requireActivity().layoutInflater, null, false)
        adapter = VillaAdapter {
            onSelectDialog?.onSelect(it)
            dismiss()
        }

        binding.recyclerVillas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerVillas.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.villaState.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arrayVillas?.add(VillaResponse(TODOS))
                        adapter?.arrayVillas?.addAll(it.data)
                        adapter?.notifyDataSetChanged()
                    }
                    is StateData.Loading -> {}
                    is StateData.Error -> {}
                    else -> {}
                }
            }
        }
        viewModel.getVillas()
        alert.setView(binding.root)
        return alert.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}