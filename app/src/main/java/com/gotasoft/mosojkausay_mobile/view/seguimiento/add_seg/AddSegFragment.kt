package com.gotasoft.mosojkausay_mobile.view.seguimiento.add_seg

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay_mobile.*
import com.gotasoft.mosojkausay_mobile.databinding.FragmentAddSegBinding
import com.gotasoft.mosojkausay_mobile.model.entities.request.SeguimientoCreateRequest
import com.gotasoft.mosojkausay_mobile.utils.getToken
import com.gotasoft.mosojkausay_mobile.view.MessageDialog
import com.gotasoft.mosojkausay_mobile.view.load.LoadDialog

class AddSegFragment: Fragment() {
    private val viewModel: AddSegViewModel by viewModels()
    private var binding: FragmentAddSegBinding? = null

    companion object {
        val TAG = AddSegFragment::class.java.name
    }
    private var token = ""

    private var loadDialog: LoadDialog? = null
    private var posMes = -1
    private var posGes = -1
    private var posTipo = -1
    private var posMod = -1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSegBinding.inflate(inflater, container, false)

        val adapterMes = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayMeses)
        val adapterGes = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayGestion)
        val adapterTipo = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayTipoSeg)
        val adapterMod = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayModeloProg)
        with(binding!!) {
            autocompleteMesAddSeg.setAdapter(adapterMes)
            autocompleteGestionAddSeg.setAdapter(adapterGes)
            autocompleteTipoAddSeg.setAdapter(adapterTipo)
            autocompleteModAddSeg.setAdapter(adapterMod)

            autocompleteMesAddSeg.setOnItemClickListener { _, _, position, _ ->
                posMes = position
            }
            autocompleteGestionAddSeg.setOnItemClickListener { _, _, position, _ ->
                posGes = position
            }
            autocompleteTipoAddSeg.setOnItemClickListener { _, _, position, _ ->
                posTipo = position
            }
            autocompleteModAddSeg.setOnItemClickListener { _, _, position, _ ->
                posMod = position
            }

            buttonAddSeg.setOnClickListener {
                val mes = autocompleteMesAddSeg.text.toString()
                val ges = autocompleteGestionAddSeg.text.toString()
                val tipo = autocompleteTipoAddSeg.text.toString()
                val mod = autocompleteModAddSeg.text.toString()
                val pro = editProgAddSeg.text.toString()
                if(mes.isEmpty() || posMes < 0) {
                    Toast.makeText(requireContext(), "Seleccione un mes", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(ges.isEmpty() || posGes < 0) {
                    Toast.makeText(requireContext(), "Seleccione una gestión", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(tipo.isEmpty() || posTipo < 0) {
                    Toast.makeText(requireContext(), "Seleccione un tipo", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(mod.isEmpty() || posMod < 0) {
                    Toast.makeText(requireContext(), "Seleccione un modelo programático", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(pro.isEmpty()) {
                    Toast.makeText(requireContext(), "Ingrese la cantidad programada", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val segCreateRequest = SeguimientoCreateRequest()
                segCreateRequest.mes = posMes + 1
                segCreateRequest.gestion = ges.toInt()
                segCreateRequest.tipo = tipo
                segCreateRequest.modelo_programatico = arrayModeloProgValue[posMod]
                segCreateRequest.programado = pro.toInt()
                viewModel.addSeg(token, segCreateRequest)

            }
        }

        flowScopes()
        return binding?.root
    }


    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    token = "Bearer $it"
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.segs.collect {
                when(it) {
                    is StateData.Success -> {
                        val mess = MessageDialog.newInstance("Éxito", "Se guardo con éxito", "Aceptar", {
                            requireActivity().onBackPressed()
                        })
                        mess.isCancelable = false
                        mess.show(childFragmentManager, MessageDialog.TAG)
                    }
                    is StateData.Error -> {
                        Log.e("SegAddError", it.toString())
                        val mess = MessageDialog.newInstance("Error", "Ocurrio un error inesperado, intente nuevamente", "Aceptar", { d ->
                            d.dismiss()
                        })
                        mess.isCancelable = true
                        mess.show(childFragmentManager, MessageDialog.TAG)
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.isCancelable = false
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
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