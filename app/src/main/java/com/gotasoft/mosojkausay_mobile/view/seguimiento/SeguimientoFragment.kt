package com.gotasoft.mosojkausay_mobile.view.seguimiento

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay_mobile.*
import com.gotasoft.mosojkausay_mobile.databinding.FragmentSeguimientoBinding
import com.gotasoft.mosojkausay_mobile.utils.getToken
import com.gotasoft.mosojkausay_mobile.view.load.LoadDialog
import com.gotasoft.mosojkausay_mobile.view.seguimiento.add_seg.AddSegFragment
import com.gotasoft.mosojkausay_mobile.view.seguimiento.edit_seg.EditSegFragment

class SeguimientoFragment: Fragment() {
    private val viewModel: SeguimientoViewModel by viewModels()
    private var binding: FragmentSeguimientoBinding? = null
    companion object {
        val TAG = SeguimientoFragment::class.java.name
    }

    private var token = ""
    private var adapter: SeguimientoAdapter? = null
    private var posGestion = 0
    private var posTipo = 0
    private var activo = 1

    private var loadDialog: LoadDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeguimientoBinding.inflate(inflater, container, false)
        adapter = SeguimientoAdapter(edit = {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.containerSeg, EditSegFragment.newInstance(it, it.id), EditSegFragment.TAG)
                .addToBackStack(EditSegFragment.TAG)
                .commit()
        }, activar = {
            AlertDialog.Builder(requireContext())
                .setTitle("Activar")
                .setMessage("¿Desea activar este seguimiento?")
                .setPositiveButton("Aceptar") { d, _ ->
                    viewModel.activeSegimiento(token, it.id)
                    d?.dismiss()
                }
                .setNegativeButton("Cancelar") { d, _ ->
                    d?.dismiss()
                }.show()
        }, desactivar = {
            AlertDialog.Builder(requireContext())
                .setTitle("Desactivar")
                .setMessage("¿Desea desactivar este seguimiento?")
                .setPositiveButton("Aceptar") { d, _ ->
                    viewModel.inactiveSegimiento(token, it.id)
                    d?.dismiss()
                }
                .setNegativeButton("Cancelar") { d, _ ->
                    d?.dismiss()
                }.show()
        })
        with(binding!!) {
            recyclerSeg.layoutManager = LinearLayoutManager(requireContext())
            recyclerSeg.adapter = adapter
            val adapterGestion = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayGestion)
            val adapterTipo = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayTipoSeg)
            autocompleteGestionSeg.setAdapter(adapterGestion)
            autocompleteGestionSeg.setText(arrayGestion[0], false)
            autocompleteTipoSeg.setAdapter(adapterTipo)
            autocompleteTipoSeg.setText(arrayTipoSeg[0], false)

            autocompleteGestionSeg.setOnItemClickListener { _, _, position, _ ->
                posGestion = position
                viewModel.getSegs(token, arrayGestion[posGestion], arrayTipoSeg[posTipo], activo)
            }
            autocompleteTipoSeg.setOnItemClickListener { _, _, position, _ ->
                posTipo = position
                viewModel.getSegs(token, arrayGestion[posGestion], arrayTipoSeg[posTipo], activo)
            }
            switchActivoSeg.setOnClickListener {
                activo = if(activo==1) 0 else 1
                viewModel.getSegs(token, arrayGestion[posGestion], arrayTipoSeg[posTipo], activo)
            }
            fabAddSeg.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.containerSeg, AddSegFragment(), AddSegFragment.TAG)
                    .addToBackStack(AddSegFragment.TAG)
                    .commit()
            }

            swipeSeg.setOnRefreshListener {
                viewModel.getSegs(token, arrayGestion[posGestion], arrayTipoSeg[posTipo], activo)
            }
        }
        flowScopes()
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        if(token.isNotEmpty()) {
            viewModel.getSegs(token, arrayGestion[posGestion], arrayTipoSeg[posTipo], 1)
        }
    }
    private var isLoaded = false

    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    token = "Bearer $it"
                    if(!isLoaded) {
                        viewModel.getSegs(token, arrayGestion[0], arrayTipoSeg[0], 1)
                    }
                    isLoaded = true
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.segs.collect {
                when(it) {
                    is StateData.Success -> {
                        it.data.sortBy { seg -> seg.mes }
                        adapter?.arraySeg = it.data
                        adapter?.notifyDataSetChanged()
                        binding?.swipeSeg?.isRefreshing = false
                        if (activo == 1) {
                            binding?.textEstadoSeguimiento?.text = getString(R.string.segs_activos)
                            binding?.textEstadoSeguimiento?.setTextColor(requireActivity().getColor(R.color.active))
                        } else {
                            binding?.textEstadoSeguimiento?.text = getString(R.string.segs_desactivos)
                            binding?.textEstadoSeguimiento?.setTextColor(requireActivity().getColor(R.color.gray))
                        }
                    }
                    is StateData.Error -> {
                        Log.e("SegError", it.toString())
                        binding?.swipeSeg?.isRefreshing = false
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        if(loadDialog == null) {
                            loadDialog = LoadDialog()
                            loadDialog?.isCancelable = false
                            loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                        }
                    }
                    StateData.None -> {
                        if(loadDialog != null) {
                            loadDialog?.dismiss()
                            loadDialog = null
                        }

                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.activeSeg.collect {
                when(it) {
                    is StateData.Success -> {
                        viewModel.getSegs(token, arrayGestion[posGestion], arrayTipoSeg[posTipo], activo)
                    }
                    is StateData.Error -> {
                        Log.e("SegError", it.toString())
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        if(loadDialog == null) {
                            loadDialog = LoadDialog()
                            loadDialog?.isCancelable = false
                            loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                        }
                    }
                    StateData.None -> {
                        if(loadDialog != null) {
                            loadDialog?.dismiss()
                            loadDialog = null
                        }

                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.inactiveSeg.collect {
                when(it) {
                    is StateData.Success -> {
                        viewModel.getSegs(token, arrayGestion[posGestion], arrayTipoSeg[posTipo], activo)
                    }
                    is StateData.Error -> {
                        Log.e("SegError", it.toString())
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        if(loadDialog == null) {
                            loadDialog = LoadDialog()
                            loadDialog?.isCancelable = false
                            loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                        }
                    }
                    StateData.None -> {
                        if(loadDialog != null) {
                            loadDialog?.dismiss()
                            loadDialog = null
                        }

                    }
                }
            }
        }
    }
}