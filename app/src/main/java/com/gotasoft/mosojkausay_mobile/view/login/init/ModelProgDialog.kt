package com.gotasoft.mosojkausay_mobile.view.login.init

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
import com.gotasoft.mosojkausay_mobile.databinding.DialodConoceModelosBinding

class ModelProgDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alert = AlertDialog.Builder(requireContext())
        val binding = DialodConoceModelosBinding.inflate(requireActivity().layoutInflater, null, false)

        binding.imageCreciendo.setOnClickListener {
            openWeb("https://mosojkausay.org/programas/creciendo-contigo.html")
        }
        binding.imageNinez.setOnClickListener {
            openWeb("https://mosojkausay.org/programas/ninez-segura-y-protegida.html")
        }
        binding.imageMequiero.setOnClickListener {
            openWeb("https://mosojkausay.org/programas/me-quiero-me-cuido.html")
        }
        binding.imagePacto.setOnClickListener {
            openWeb("https://mosojkausay.org/programas/pacto.html")
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

    private fun openWeb(url: String) {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }
}