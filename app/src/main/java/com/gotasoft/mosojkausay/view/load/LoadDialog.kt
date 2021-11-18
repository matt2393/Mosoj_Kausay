package com.gotasoft.mosojkausay.view.load

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.gotasoft.mosojkausay.databinding.DialogLoadBinding

class LoadDialog: DialogFragment() {
    companion object {
        val TAG = LoadDialog::class.java.name
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alert = AlertDialog.Builder(requireContext())
        val bind = DialogLoadBinding.inflate(requireActivity().layoutInflater, null, false)

        alert.setView(bind.root)
        return alert.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}