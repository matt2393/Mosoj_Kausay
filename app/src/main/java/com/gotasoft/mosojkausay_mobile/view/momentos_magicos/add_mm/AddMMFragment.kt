package com.gotasoft.mosojkausay_mobile.view.momentos_magicos.add_mm

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.databinding.FragmentAddMmBinding
import com.gotasoft.mosojkausay_mobile.model.entities.request.MMRequest
import com.gotasoft.mosojkausay_mobile.utils.getToken
import com.gotasoft.mosojkausay_mobile.view.MessageDialog
import com.gotasoft.mosojkausay_mobile.view.load.LoadDialog
import java.util.*

class AddMMFragment: Fragment() {
    private val viewModel: AddMMViewModel by viewModels()
    companion object{
        val TAG = AddMMFragment::class.java.name
    }

    private var token = ""

    private var loadDialog: LoadDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddMmBinding.inflate(inflater, container, false)

        val adapterModelo = ArrayAdapter(requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf("Creciendo Contigo",
                "Niñez Segura y Protegida",
                "Me Quiero Me Cuido", "Pacto",
                "Mecanismo de Protección"))

        val adapterTipo = ArrayAdapter(requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf("En momentos clave (hitos)",
                "En cierre de procesos",
                "En sesiones de programa", "En ferias comunitarias"))

        val adapterRes= ArrayAdapter(requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf("R1", "R2", "R3"))

        with(binding) {
            editModeloAddMM.setAdapter(adapterModelo)
            editTipoAddMM.setAdapter(adapterTipo)
            editResAddMM.setAdapter(adapterRes)

            editFechaAddMM.setOnClickListener {
                showDialogPicker(editFechaAddMM)
            }

            buttonGuardarMM.setOnClickListener {
                val mod = editModeloAddMM.text.toString()
                val tipo = editTipoAddMM.text.toString()
                val res = editResAddMM.text.toString()
                val childN = editChildNumberAddMM.text.toString()
                val nomb = editNomAddMM.text.toString()
                val reff = editRefFamAddMM.text.toString()
                val edad = editEdadAddMM.text.toString()
                val fecha = editFechaAddMM.text.toString()
                val ocupa = editOcupacionAddMM.text.toString()
                val tes1 = editTes1AddMM.text.toString()
                val tes2 = editTes2AddMM.text.toString()
                val tes3 = editTes3AddMM.text.toString()
                val tes4 = editTes4AddMM.text.toString()

                if(mod.isEmpty()) {
                    Toast.makeText(requireContext(), "Seleccione un modelo programático", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(tipo.isEmpty()) {
                    Toast.makeText(requireContext(), "Seleccione un tipo de momento mágico", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(res.isEmpty()) {
                    Toast.makeText(requireContext(), "Seleccione un resultado intermedio", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(childN.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba un ChildNumber", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(nomb.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba un nombre", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(reff.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba la referencia familiar", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(edad.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba la edad", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(fecha.isEmpty()) {
                    Toast.makeText(requireContext(), "Seleccione una fecha", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(ocupa.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba la ocupación", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val mmRequest = MMRequest()
                mmRequest.child_number = childN
                mmRequest.modelo_programatico = mod
                mmRequest.tipo_momento_magico = tipo
                mmRequest.resultado_intermedio = res
                mmRequest.nombre_completo = nomb
                mmRequest.referencia_familiar = reff
                mmRequest.edad = edad.toInt()
                mmRequest.fecha_envio = fecha
                mmRequest.ocupacion = ocupa
                mmRequest.testimonio_1 = tes1
                mmRequest.testimonio_2 = tes2
                mmRequest.testimonio_3 = tes3
                mmRequest.testimonio_4 = tes4
                mmRequest.consentimiento = switchConsentiAddMM.isChecked
                viewModel.addMM(token, mmRequest)
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
            viewModel.mm.collect {
                when(it) {
                    is StateData.Success -> {
                        val mess = MessageDialog.newInstance("Éxito", "Se guardo con éxito", "Aceptar", {
                            requireActivity().onBackPressed()
                        })
                        mess.isCancelable = false
                        mess.show(childFragmentManager, MessageDialog.TAG)

                    }
                    is StateData.Error -> {
                        val mess = MessageDialog.newInstance("Error", "Ocurrio un error inesperado, intente nuevamente", "Aceptar", { d ->
                            d.dismiss()
                        })
                        mess.isCancelable = true
                        mess.show(childFragmentManager, MessageDialog.TAG)
                    }
                    StateData.Loading -> {
                        if(loadDialog == null) {
                            loadDialog = LoadDialog()
                            loadDialog?.isCancelable = false
                            loadDialog?.show(childFragmentManager, "Load2")
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

    private fun showDialogPicker(edit: EditText) {
        val c = Calendar.getInstance()
        DatePickerDialog(requireContext(),
            { _, year, month, dayOfMonth ->
                val fecha = "$year-${month + 1}-$dayOfMonth"
                edit.setText(fecha)
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH))
            .show()
    }
}