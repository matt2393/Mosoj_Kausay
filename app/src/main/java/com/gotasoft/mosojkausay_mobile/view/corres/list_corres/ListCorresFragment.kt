package com.gotasoft.mosojkausay_mobile.view.corres.list_corres

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.estrelladelsur.apptecnico.map.MapDialog
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.databinding.FragmentListCorresBinding
import com.gotasoft.mosojkausay_mobile.model.entities.request.ValidarCorresRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.DfcResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.ReplyResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.UnavailableResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.WelcomeResponse
import com.gotasoft.mosojkausay_mobile.utils.TipoPersonal
import com.gotasoft.mosojkausay_mobile.utils.getToken
import com.gotasoft.mosojkausay_mobile.utils.tokenTipoUs
import com.gotasoft.mosojkausay_mobile.view.corres.CorresDialog
import com.gotasoft.mosojkausay_mobile.view.load.LoadDialog
import com.gotasoft.mosojkausay_mobile.view.participantes.list_participantes.ContactoDialog

class ListCorresFragment : Fragment() {
    private val viewModel: ListCorresViewModel by viewModels()
    private var binding: FragmentListCorresBinding? = null

    companion object {
        val TAG = ListCorresFragment::class.java.name
        private const val TIPO = "Tipo"
        private const val PLANILLA_ID = "PlanillaId"
        fun newInstance(tipo: String, planillaId: String) = ListCorresFragment().apply {
            arguments = Bundle().apply {
                putString(TIPO, tipo)
                putString(PLANILLA_ID, planillaId)
            }
        }
    }

    private var tipo: String = "reply"
    private var planilla_id: String = ""
    private var loadDialog: LoadDialog? = null
    private var token = ""

    private var adapterReply: ListCorresAdapter<ReplyResponse>? = null
    private var adapterWelcome: ListCorresAdapter<WelcomeResponse>? = null
    private var adapterDfc: ListCorresAdapter<DfcResponse>? = null
    private var adapterUnavailable: ListCorresAdapter<UnavailableResponse>? = null

    private var tipoUs = 0
    private var value = ""
    private var validado = "pendiente"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCorresBinding.inflate(inflater, container, false)

        arguments?.let {
            tipo = it.getString(TIPO, "reply")
            planilla_id = it.getString(PLANILLA_ID, "")
        }
        requireActivity().title = planilla_id.uppercase()
        adapterReply = ListCorresAdapter(bind = { re, b ->
            b.textNomItemCorres.text = re.participant_name
            b.textChildNumberItemCorres.text = re.child_number
            b.textIdCorres.text = re.mcs_id
            b.textValidadoItemCorres.background = when (re.validacion) {
                "validado" -> {
                    b.fabPendienteItemCorres.show()
                    b.fabValidarItemCorres.hide()
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_validado)
                }
                else -> {
                    b.fabPendienteItemCorres.hide()
                    b.fabValidarItemCorres.show()
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_pendiente)
                }
            }
            b.textValidadoItemCorres.text = re.validacion.uppercase()
            if (re.latitud.isNullOrEmpty() || re.longitud.isNullOrEmpty()) {
                b.fabMapItemCorres.hide()
            } else {
                b.fabMapItemCorres.show()
            }
            if (re.referencia_familiar_telefono.isNullOrEmpty() && re.padre_telefono.isNullOrEmpty() && re.madre_telefono.isNullOrEmpty()) {
                b.fabContactoItemCorres.hide()
            } else {
                b.fabContactoItemCorres.show()
            }
        }, ver = {
            CorresDialog.newInstance(it)
                .show(childFragmentManager, CorresDialog.TAG)
        }, validar = {
            confim(it.mcs_id, "Validar", it.participant_name, "validado")
        }, pendiente = {
            confim(it.mcs_id, "Revertir", it.participant_name, "pendiente")
        }, map = {
            MapDialog.newInstance(it.latitud ?: "", it.longitud ?: "", it.participant_name ?: "")
                .show(childFragmentManager, MapDialog.TAG)
        }, contact = {
            ContactoDialog.newInstance(it.referencia_familiar_telefono ?: "", it.padre_telefono ?: "", it.madre_telefono ?: "")
                .show(childFragmentManager, ContactoDialog.TAG)
        })
        adapterWelcome = ListCorresAdapter(bind = { wel, b ->
            b.textNomItemCorres.text = wel.participant_name
            b.textChildNumberItemCorres.text = wel.child_number
            b.textIdCorres.text = wel.mcs_id
            b.textValidadoItemCorres.background = when (wel.validacion) {
                "validado" -> {
                    b.fabPendienteItemCorres.show()
                    b.fabValidarItemCorres.hide()
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_validado)
                }
                else -> {
                    b.fabPendienteItemCorres.hide()
                    b.fabValidarItemCorres.show()
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_pendiente)
                }
            }
            b.textValidadoItemCorres.text = wel.validacion.uppercase()
            if (wel.latitud.isNullOrEmpty() || wel.longitud.isNullOrEmpty()) {
                b.fabMapItemCorres.hide()
            } else {
                b.fabMapItemCorres.show()
            }
            if (wel.referencia_familiar_telefono.isNullOrEmpty() && wel.padre_telefono.isNullOrEmpty() && wel.madre_telefono.isNullOrEmpty()) {
                b.fabContactoItemCorres.hide()
            } else {
                b.fabContactoItemCorres.show()
            }
        }, ver = {
            CorresDialog.newInstance(it)
                .show(childFragmentManager, CorresDialog.TAG)
        }, validar = {
            confim(it.mcs_id, "Validar", it.participant_name, "validado")
        }, pendiente = {
            confim(it.mcs_id, "Revertir", it.participant_name, "pendiente")
        }, map = {
            MapDialog.newInstance(it.latitud ?: "", it.longitud ?: "", it.participant_name ?: "")
                .show(childFragmentManager, MapDialog.TAG)
        }, contact = {
            ContactoDialog.newInstance(it.referencia_familiar_telefono ?: "", it.padre_telefono ?: "", it.madre_telefono ?: "")
                .show(childFragmentManager, ContactoDialog.TAG)
        })
        adapterDfc = ListCorresAdapter(bind = { dfc, b ->
            b.textNomItemCorres.text = dfc.participant_name
            b.textChildNumberItemCorres.text = dfc.child_number
            b.textIdCorres.text = dfc.mcs_id

            b.textValidadoItemCorres.background = when (dfc.validacion) {
                "validado" -> {
                    b.fabPendienteItemCorres.show()
                    b.fabValidarItemCorres.hide()
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_validado)
                }
                else -> {
                    b.fabPendienteItemCorres.hide()
                    b.fabValidarItemCorres.show()
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_pendiente)
                }
            }
            b.textValidadoItemCorres.text = dfc.validacion.uppercase()
            if (dfc.latitud.isNullOrEmpty() || dfc.longitud.isNullOrEmpty()) {
                b.fabMapItemCorres.hide()
            } else {
                b.fabMapItemCorres.show()
            }
            if (dfc.referencia_familiar_telefono.isNullOrEmpty() && dfc.padre_telefono.isNullOrEmpty() && dfc.madre_telefono.isNullOrEmpty()) {
                b.fabContactoItemCorres.hide()
            } else {
                b.fabContactoItemCorres.show()
            }
        }, ver = {
            CorresDialog.newInstance(it)
                .show(childFragmentManager, CorresDialog.TAG)
        }, validar = {
            confim(it.mcs_id, "Validar", it.participant_name, "validado")
        }, pendiente = {
            confim(it.mcs_id, "Revertir", it.participant_name, "pendiente")
        }, map = {
            MapDialog.newInstance(it.latitud ?: "", it.longitud ?: "", it.participant_name ?: "")
                .show(childFragmentManager, MapDialog.TAG)
        }, contact = {
            ContactoDialog.newInstance(it.referencia_familiar_telefono ?: "", it.padre_telefono ?: "", it.madre_telefono ?: "")
                .show(childFragmentManager, ContactoDialog.TAG)
        })
        adapterUnavailable = ListCorresAdapter(bind = { una, b ->
            b.textNomItemCorres.text = una.participant_name
            b.textChildNumberItemCorres.text = una.child_number
            b.textIdCorres.text = una.id.toString()
            b.textValidadoItemCorres.background = when (una.validacion) {
                "validado" -> {
                    b.fabPendienteItemCorres.show()
                    b.fabValidarItemCorres.hide()
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_validado)
                }
                else -> {
                    b.fabPendienteItemCorres.hide()
                    b.fabValidarItemCorres.show()
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_pendiente)
                }
            }
            b.textValidadoItemCorres.text = una.validacion.uppercase()
            if (una.latitud.isNullOrEmpty() || una.longitud.isNullOrEmpty()) {
                b.fabMapItemCorres.hide()
            } else {
                b.fabMapItemCorres.show()
            }
            if (una.referencia_familiar_telefono.isNullOrEmpty() && una.padre_telefono.isNullOrEmpty() && una.madre_telefono.isNullOrEmpty()) {
                b.fabContactoItemCorres.hide()
            } else {
                b.fabContactoItemCorres.show()
            }
        }, ver = {
            CorresDialog.newInstance(it)
                .show(childFragmentManager, CorresDialog.TAG)
        }, validar = {
            confim(it.id.toString(), "Validar", it.participant_name, "validado")
        }, pendiente = {
            confim(it.id.toString(), "Revertir", it.participant_name, "pendiente")
        }, map = {
            MapDialog.newInstance(it.latitud ?: "", it.longitud ?: "", it.participant_name ?: "")
                .show(childFragmentManager, MapDialog.TAG)
        }, contact = {
            ContactoDialog.newInstance(it.referencia_familiar_telefono ?: "", it.padre_telefono ?: "", it.madre_telefono ?: "")
                .show(childFragmentManager, ContactoDialog.TAG)
        })


        binding?.recyclerListCorres?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerListCorres?.adapter = when (tipo) {
            "reply" -> adapterReply
            "welcome" -> adapterWelcome
            "dfc" -> adapterDfc
            "unavailable" -> adapterUnavailable
            else -> adapterReply
        }

        binding?.switchValidadoListCorres?.setOnClickListener {
            if (binding != null && binding!!.switchValidadoListCorres.isChecked) {
                binding?.switchValidadoListCorres?.text = "Validados"
            } else {
                binding?.switchValidadoListCorres?.text = "Pendientes"
            }
            load()
        }
        binding?.fabBuscarListCorres?.setOnClickListener {
            load()
        }

        flowScopes()

        return binding?.root

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if (it != null) {
                    token = "Bearer $it"
                    tipoUs = when (it.tokenTipoUs()) {
                        TipoPersonal.PATROCINIO -> {
                            1
                        }
                        else -> {
                            0
                        }
                    }
                    load()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.reply.collect {
                when (it) {
                    is StateData.Success -> {
                        adapterReply?.arrayData = it.data
                        adapterReply?.notifyDataSetChanged()
                    }
                    is StateData.Error -> {
                        Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    StateData.None -> {
                        if (loadDialog != null) {
                            loadDialog?.dismiss()
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.welcome.collect {
                when (it) {
                    is StateData.Success -> {
                        adapterWelcome?.arrayData = it.data
                        adapterWelcome?.notifyDataSetChanged()
                    }
                    is StateData.Error -> {
                        Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    StateData.None -> {
                        if (loadDialog != null) {
                            loadDialog?.dismiss()
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.dfc.collect {
                when (it) {
                    is StateData.Success -> {
                        adapterDfc?.arrayData = it.data
                        adapterDfc?.notifyDataSetChanged()
                    }
                    is StateData.Error -> {
                        Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    StateData.None -> {
                        if (loadDialog != null) {
                            loadDialog?.dismiss()
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.unavailable.collect {
                when (it) {
                    is StateData.Success -> {
                        adapterUnavailable?.arrayData = it.data
                        adapterUnavailable?.notifyDataSetChanged()
                    }
                    is StateData.Error -> {
                        Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    StateData.None -> {
                        if (loadDialog != null) {
                            loadDialog?.dismiss()
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.validado.collect {
                when (it) {
                    is StateData.Success -> {
                        Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT)
                            .show()
                        if (it.data.success) {
                            load()
                        }
                    }
                    is StateData.Error -> {
                        Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    StateData.None -> {
                        if (loadDialog != null) {
                            loadDialog?.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun load() {
        value = binding?.editBuscarListCorres?.text.toString()
        validado = if (binding != null && binding!!.switchValidadoListCorres.isChecked) {
            "validado"
        } else {
            "pendiente"

        }
        when (tipoUs) {
            1 -> {
                loadCorres()
            }
            0 -> {
                loadMisCorres()
            }
        }
    }

    private fun loadMisCorres() {
        when (tipo) {
            "reply" -> {
                viewModel.getMisReply(token, value, planilla_id, validado)
            }
            "welcome" -> {
                viewModel.getMisWelcome(token, value, planilla_id, validado)
            }
            "dfc" -> {
                viewModel.getMisDfc(token, value, planilla_id, validado)
            }
            "unavailable" -> {
                viewModel.getMisUnavailable(token, value, planilla_id, validado)
            }
        }
    }

    private fun loadCorres() {
        when (tipo) {
            "reply" -> {
                viewModel.getReply(token, value, planilla_id, validado)
            }
            "welcome" -> {
                viewModel.getWelcome(token, value, planilla_id, validado)
            }
            "dfc" -> {
                viewModel.getDfc(token, value, planilla_id, validado)
            }
            "unavailable" -> {
                viewModel.getUnavailable(token, value, planilla_id, validado)
            }
        }
    }

    private fun confim(id: String, mess: String, nombre: String, validadoAux: String) {
        val mensaje = "¿Esta seguro de $mess la correspondencia de $nombre?"
        AlertDialog.Builder(requireContext())
            .setTitle(mess)
            .setMessage(mensaje)
            .setPositiveButton("Aceptar") { d, _ ->
                val request = ValidarCorresRequest(tipo, validadoAux)
                viewModel.validarCorres(token, id, request)
                d?.dismiss()
            }
            .setNegativeButton("Cancelar") { d, _ ->
                d?.dismiss()
            }.show()
    }
}