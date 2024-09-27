package com.gotasoft.mosojkausay_mobile.view.seguimiento.edit_seg

import android.R
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
import com.gotasoft.mosojkausay_mobile.databinding.FragmentEditSegBinding
import com.gotasoft.mosojkausay_mobile.model.entities.request.SeguimientoEditRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.SeguimientoResponse
import com.gotasoft.mosojkausay_mobile.utils.getToken
import com.gotasoft.mosojkausay_mobile.view.MessageDialog
import com.gotasoft.mosojkausay_mobile.view.load.LoadDialog

class EditSegFragment: Fragment() {
    private val viewModel: EditSegViewModel by viewModels()
    companion object {
        val TAG = EditSegFragment::class.java.simpleName
        private val SEG = "SEG"
        private val ID = "ID"
        fun newInstance(seg: SeguimientoResponse, id: Int) = EditSegFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SEG, seg)
                putInt(ID,id)
            }
        }
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
        val binding = FragmentEditSegBinding.inflate(inflater, container, false)
        val seg = arguments?.getParcelable<SeguimientoResponse>(SEG)
        val id = arguments?.getInt(ID)
        val adapterMes = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, arrayMeses)
        val adapterGes = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, arrayGestion)
        val adapterTipo = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, arrayTipoSeg)
        val adapterMod = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, arrayModeloProg)
        with(binding) {
            if(id!=null && id > 0 && seg!=null) {
                autocompleteMesEditSeg.setAdapter(adapterMes)
                autocompleteGestionEditSeg.setAdapter(adapterGes)
                autocompleteTipoEditSeg.setAdapter(adapterTipo)
                autocompleteModEditSeg.setAdapter(adapterMod)

                autocompleteMesEditSeg.setOnItemClickListener { _, _, position, _ ->
                    posMes = position
                }
                autocompleteGestionEditSeg.setOnItemClickListener { _, _, position, _ ->
                    posGes = position
                }
                autocompleteTipoEditSeg.setOnItemClickListener { _, _, position, _ ->
                    posTipo = position
                }
                autocompleteModEditSeg.setOnItemClickListener { _, _, position, _ ->
                    posMod = position
                }

                arrayModeloProgValue.forEachIndexed { index, s ->
                    if(seg.modelo_programatico == s) {
                        posMod = index
                        return@forEachIndexed
                    }
                }
                posMes = seg.mes - 1
                autocompleteGestionEditSeg.setText(seg.gestion.toString(),false)
                autocompleteMesEditSeg.setText(arrayMeses[seg.mes-1], false)
                autocompleteTipoEditSeg.setText(seg.tipo, false)
                autocompleteModEditSeg.setText(arrayModeloProg[posMod], false)
                editProgEditSeg.setText(seg.programado.toString())
                editEjecEditSeg.setText(seg.ejecutado.toString())


                buttonEditSeg.setOnClickListener {
                    val mes = autocompleteMesEditSeg.text.toString()
                    val ges = autocompleteGestionEditSeg.text.toString()
                    val tipo = autocompleteTipoEditSeg.text.toString()
                    val mod = autocompleteModEditSeg.text.toString()
                    val pro = editProgEditSeg.text.toString()
                    val eje = editEjecEditSeg.text.toString()
                    if(mes.isEmpty()) {
                        Toast.makeText(requireContext(), "Seleccione un mes", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if(ges.isEmpty()) {
                        Toast.makeText(requireContext(), "Seleccione una gestión", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if(tipo.isEmpty()) {
                        Toast.makeText(requireContext(), "Seleccione un tipo", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if(mod.isEmpty()) {
                        Toast.makeText(requireContext(), "Seleccione un modelo programático", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if(pro.isEmpty()) {
                        Toast.makeText(requireContext(), "Ingrese la cantidad programada", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if(eje.isEmpty()) {
                        Toast.makeText(requireContext(), "Ingrese la cantidad ejecutada", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    if(eje.toInt() > pro.toInt()) {
                        Toast.makeText(requireContext(), "La cantidad ejecutada no puede ser mayor al programado", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    val segCreateRequest = SeguimientoEditRequest()
                    segCreateRequest.mes = posMes + 1
                    segCreateRequest.gestion = ges.toInt()
                    segCreateRequest.tipo = tipo
                    segCreateRequest.modelo_programatico = arrayModeloProgValue[posMod]
                    segCreateRequest.programado = pro.toInt()
                    segCreateRequest.ejecutado = eje.toInt()
                    segCreateRequest.activo = seg.activo
                    viewModel.editSeg(token, id, segCreateRequest)
                }

            } else {
                Toast.makeText(requireContext(), "Error al recuperar los datos", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }
        }

        flowScopes()
        return binding.root
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
                        val mess = MessageDialog.newInstance("Éxito", "Se actualizo con éxito", "Aceptar", {
                            requireActivity().onBackPressed()
                        })
                        mess.isCancelable = false
                        mess.show(childFragmentManager, MessageDialog.TAG)

                    }
                    is StateData.Error -> {
                        Log.e("SegEditError", it.toString())
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