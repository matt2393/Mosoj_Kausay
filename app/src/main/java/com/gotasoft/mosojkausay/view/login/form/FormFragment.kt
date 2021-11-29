package com.gotasoft.mosojkausay.view.login.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.FragmentFormBinding
import com.gotasoft.mosojkausay.model.entities.request.PatrocinadorRequest
import com.gotasoft.mosojkausay.utils.getToken
import com.gotasoft.mosojkausay.view.load.LoadDialog
import kotlinx.coroutines.flow.collect

class FormFragment: Fragment() {

    private val viewModel: FormViewModel by viewModels()
    companion object {
        val TAG = FormFragment::class.java.name
    }

    private var loadDialog: LoadDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentFormBinding.inflate(inflater, container, false)

        with(bind) {
            buttonEnviarPatro.setOnClickListener {
                val nom = editNomPatro.text.toString()
                val email = editEmailPatro.text.toString()
                val asunto = editAsuntoPatro.text.toString()
                val mess = editMessPatro.text.toString()
                if(nom.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba su nombre", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(email.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba su correo electónico", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(asunto.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba un asunto", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(mess.isEmpty()) {
                    Toast.makeText(requireContext(), "Escriba el mensaje", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val req = PatrocinadorRequest()
                req.nombre_completo = nom
                req.asunto = asunto
                req.correo = email
                req.mensaje = mess

                viewModel.registrarPatrp( req)
            }
        }
        flowScopes()

        return bind.root
    }

    private fun flowScopes() {

        lifecycleScope.launchWhenStarted {
            viewModel.patro.collect {
                when(it) {
                    is StateData.Success -> {
                        Toast.makeText(requireContext(), "Se registro con éxito", Toast.LENGTH_SHORT).show()
                        requireActivity().onBackPressed()
                    }
                    is StateData.Error -> {
                        Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.isCancelable = false
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    StateData.None -> {
                        if(loadDialog!=null) {
                            loadDialog?.dismiss()
                            loadDialog = null
                        }
                    }
                }
            }
        }
    }
}