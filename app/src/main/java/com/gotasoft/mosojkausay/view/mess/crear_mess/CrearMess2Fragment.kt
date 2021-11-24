package com.gotasoft.mosojkausay.view.mess.crear_mess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.FragmentCrearMess2Binding
import com.gotasoft.mosojkausay.model.entities.request.MessageRequest
import com.gotasoft.mosojkausay.utils.getToken
import kotlinx.coroutines.flow.collect

class CrearMess2Fragment: Fragment() {
    private val viewModel: CrearMessViewModel by viewModels()
    companion object {
        val TAG = CrearMess2Fragment::class.java.name
        private const val ASUNTO = "ASUNTO"
        private const val CONT = "CONT"
        fun newInstance(asunto: String, contenido: String) = CrearMess2Fragment().apply {
            arguments = Bundle().apply {
                putString(ASUNTO, asunto)
                putString(CONT, contenido)
            }
        }

    }

    private var adapter: PersonalAdapter? = null
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentCrearMess2Binding.inflate(inflater, container, false)

        var asunto = ""
        var contenido = ""
        arguments?.let {
            asunto = it.getString(ASUNTO, "")
            contenido = it.getString(CONT, "")
        }

        adapter = PersonalAdapter()
        bind.recyclerCrearMess.layoutManager = LinearLayoutManager(requireContext())
        bind.recyclerCrearMess.adapter = adapter

        bind.buttonCrearMess.setOnClickListener {
            val destinatarios = arrayListOf<MessageRequest.Destinatario>()
            adapter?.arrayPersonal?.forEach {
                destinatarios.add(MessageRequest.Destinatario(ci = it.personal.ci))
            }
            val mess = MessageRequest(asunto, contenido, destinatarios)
            viewModel.addMess(token, mess)
            //requireActivity().finish()
        }

        flowScopes()
        return bind.root
    }

    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                token = "Bearer $it"
                viewModel.getPersonal(token)
            }
        }
        lifecycleScope.launchWhenStarted {

            viewModel.personal.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arrayPersonal = it.data
                        adapter?.notifyItemRangeInserted(0, it.data.size)
                    }
                    is StateData.Error -> {
                        Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {

                    }
                    StateData.None -> {

                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.mess.collect {
                when(it) {
                    is StateData.Success -> {
                        Toast.makeText(requireContext(), "Se envio con exito...", Toast.LENGTH_SHORT).show()
                        requireActivity().finish()
                    }
                    is StateData.Error -> {
                        Toast.makeText(requireContext(), "Error al enviar el mensaje...", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {

                    }
                    StateData.None -> {

                    }
                }
            }
        }
    }

}