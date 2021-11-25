package com.gotasoft.mosojkausay.view.participantes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.databinding.FragmentParticipantesBinding

class ParticipantesFragment: Fragment() {
    companion object {
        val TAG = ParticipantesFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentParticipantesBinding.inflate(inflater, container, false)
        val adapter = ParticipantesAdapter()
        bind.recyclerParticipantes.layoutManager = LinearLayoutManager(requireContext())
        bind.recyclerParticipantes.adapter = adapter

        val arrayGes = arrayListOf("2021")
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayGes)
        bind.autocompleteGestionParticipantes.setAdapter(arrayAdapter)
        bind.autocompleteGestionParticipantes.setText("2021", false)

        return bind.root
    }
}