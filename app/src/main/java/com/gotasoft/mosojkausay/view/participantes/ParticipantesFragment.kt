package com.gotasoft.mosojkausay.view.participantes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.FragmentParticipantesBinding
import kotlinx.coroutines.flow.collect
import java.util.Calendar

class ParticipantesFragment: Fragment() {
    private val partVM: PartVM by viewModels()
    companion object {
        val TAG = ParticipantesFragment::class.java.name
    }

    private var adapter: ParticipantesAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentParticipantesBinding.inflate(inflater, container, false)
        adapter = ParticipantesAdapter()

        val arrayGes = getGestiones()
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayGes)

        with(bind) {
            recyclerParticipantes.layoutManager = LinearLayoutManager(requireContext())
            recyclerParticipantes.adapter = adapter

            autocompleteGestionParticipantes.setAdapter(arrayAdapter)
            autocompleteGestionParticipantes.setText(arrayGes.firstOrNull(), false)

            autocompleteGestionParticipantes.setOnItemClickListener { parent, view, position, id ->
                partVM.getTotales(arrayGes[position])
            }

        }
        flowScopes()
        if (arrayGes.isNotEmpty()) {
            partVM.getTotales(arrayGes.first())
        }
        return bind.root
    }

    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            partVM.totales.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.listTotales = it.data.toMutableList()
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

    private fun getGestiones(): List<String> {
        val yearCurrent = Calendar.getInstance().get(Calendar.YEAR)
        val yearInit = 2022
        val listYears = mutableListOf<String>()
        for (i in yearInit..yearCurrent) {
            listYears.add(i.toString())
        }
        return listYears
    }
}