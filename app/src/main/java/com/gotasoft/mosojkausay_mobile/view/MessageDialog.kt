package com.gotasoft.mosojkausay_mobile.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.gotasoft.mosojkausay_mobile.databinding.DialogMessageBinding

class MessageDialog: DialogFragment() {

    private var binding: DialogMessageBinding? = null

    companion object {
        val TAG = MessageDialog::class.java.simpleName
        private const val TITLE = "Title"
        private const val MESS = "Mess"
        private const val POSS_TEXt = "POSS_TEXt"
        private const val NEG_TEXT = "NEG_TEXT"
        private const val AVAILABLE_NEGATIVE = "AVAILABLE_NEGATIVE"
        fun  newInstance(
            title: String,
            mess: String,
            positiveText: String,
            positiveClick: (DialogFragment) -> Unit,
            negativeText: String? = null,
            negativeButton: (() -> Unit)? = null) =
            MessageDialog().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putString(MESS, mess)
                    putString(POSS_TEXt, positiveText)
                    putString(NEG_TEXT, negativeText)
                    putBoolean(AVAILABLE_NEGATIVE, negativeButton != null)
                }
                onActionDialog = object: OnActionDialog {
                    override fun positiveClick(dialog: DialogFragment) {
                        positiveClick.invoke(dialog)
                    }

                    override fun negativeClick() {
                        negativeButton?.invoke()
                    }
                }
            }
    }

    interface OnActionDialog {
        fun positiveClick(dialog: DialogFragment)
        fun negativeClick()
    }

    private var onActionDialog: OnActionDialog? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alert = AlertDialog.Builder(requireContext())
        binding = DialogMessageBinding.inflate(requireActivity().layoutInflater, null, false)
        var title = ""
        var mess = ""
        var possText = ""
        var negText = ""
        var availableNegative = false
        arguments?.let {
            title = it.getString(TITLE, "")
            mess = it.getString(MESS, "")
            possText = it.getString(POSS_TEXt, "")
            negText = it.getString(NEG_TEXT, "")
            availableNegative = it.getBoolean(AVAILABLE_NEGATIVE, false)
        }
        with(binding!!) {
            textTitleDialog.text = title
            textMessageDialog.text = mess
            buttonNegativeDialog.text = negText
            buttonNegativeDialog.visibility = if (availableNegative) View.VISIBLE else View.GONE
            buttonNegativeDialog.setOnClickListener {
                onActionDialog?.negativeClick()
            }
            buttonPositiveDialog.text = possText
            buttonPositiveDialog.setOnClickListener {
                onActionDialog?.positiveClick(this@MessageDialog)
            }
        }

        alert.setView(binding?.root)
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