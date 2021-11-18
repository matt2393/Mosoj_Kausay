package com.gotasoft.mosojkausay.view.participantes.list_participantes

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.gotasoft.mosojkausay.databinding.DialogContactosBinding

class ContactoDialog: DialogFragment() {

    companion object {
        val TAG = ContactoDialog::class.java.name
        private const val REF = "REF"
        private const val PADRE = "PADRE"
        private const val MADRE = "MADRE"
        fun newInstance(ref: String, padre: String, madre: String) = ContactoDialog().apply {
            arguments = Bundle().apply {
                putString(REF, ref)
                putString(PADRE, padre)
                putString(MADRE, madre)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alert = AlertDialog.Builder(requireContext())
        val binding = DialogContactosBinding.inflate(requireActivity().layoutInflater, null, false)
        var ref = ""
        var padre = ""
        var madre = ""
        arguments?.let {
            ref = it.getString(REF, "")
            padre = it.getString(PADRE, "")
            madre = it.getString(MADRE, "")
        }
        with(binding) {
            buttonRefFamContactos.visibility = if(ref.isEmpty()) View.GONE else View.VISIBLE
            buttonPadreContactos.visibility = if(padre.isEmpty()) View.GONE else View.VISIBLE
            buttonMadreContactos.visibility = if(madre.isEmpty()) View.GONE else View.VISIBLE
            buttonRefFamContactos.setOnClickListener {
                marcarContacto(ref)
            }
            buttonPadreContactos.setOnClickListener {
                marcarContacto(padre)
            }
            buttonMadreContactos.setOnClickListener {
                marcarContacto(madre)
            }
        }

        alert.setView(binding.root)
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

    private fun marcarContacto(tel: String) {
        startActivity(
            Intent(
                "android.intent.action.DIAL",
                Uri.parse("tel:${tel}")
            )
        )
    }
}