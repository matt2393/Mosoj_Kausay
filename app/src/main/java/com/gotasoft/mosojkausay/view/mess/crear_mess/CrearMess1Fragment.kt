package com.gotasoft.mosojkausay.view.mess.crear_mess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.FragmentCrearMess1Binding

class CrearMess1Fragment: Fragment() {
    companion object {
        val TAG = CrearMess1Fragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentCrearMess1Binding.inflate(inflater, container, false)
        bind.fabExSigCrearMess.setOnClickListener {
            val asunto = bind.editTitCrearMess.text.toString()
            val contenido = bind.editMessCrearMess.text.toString()
            if(asunto.isEmpty()) {
                Toast.makeText(requireContext(), "Escriba el Asunto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(contenido.isEmpty()) {
                Toast.makeText(requireContext(), "Escriba un Contenido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.containerCrearMess,
                    CrearMess2Fragment.newInstance(asunto, contenido),
                    CrearMess2Fragment.TAG)
                .addToBackStack(CrearMess2Fragment.TAG)
                .commit()
        }
        return bind.root
    }
}