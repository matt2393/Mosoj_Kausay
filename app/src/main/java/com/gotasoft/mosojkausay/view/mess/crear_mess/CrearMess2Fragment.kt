package com.gotasoft.mosojkausay.view.mess.crear_mess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gotasoft.mosojkausay.databinding.FragmentCrearMess2Binding

class CrearMess2Fragment: Fragment() {
    companion object {
        val TAG = CrearMess2Fragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentCrearMess2Binding.inflate(inflater, container, false)
        bind.buttonCrearMess.setOnClickListener {
            requireActivity().finish()
        }
        return bind.root
    }
}