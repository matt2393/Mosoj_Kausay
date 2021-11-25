package com.gotasoft.mosojkausay.view.mess

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.*
import com.gotasoft.mosojkausay.databinding.FragmentMessBinding
import com.gotasoft.mosojkausay.utils.*
import com.gotasoft.mosojkausay.view.mess.crear_mess.CrearMessActivity
import com.gotasoft.mosojkausay.view.mess.destinatarios.DestinatariosDialog
import kotlinx.coroutines.flow.collect

class MessFragment: Fragment() {
    private val viewModel: MessViewModel by viewModels()
    private var adapter: MessAdapter? = null
    private var adapterAll: AllMessAdapter? = null
    companion object {
        val TAG = MessFragment::class.java.name
    }

    private var binding: FragmentMessBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMessBinding.inflate(inflater, container, false)
        adapter = MessAdapter()
        adapterAll = AllMessAdapter {
            DestinatariosDialog.newInstance(it.id)
                .show(childFragmentManager, DestinatariosDialog.TAG)
        }
        binding?.recyclerMess?.layoutManager = LinearLayoutManager(requireContext())
        binding?.fabCrearMess?.setOnClickListener {
            startActivity(Intent(requireContext(), CrearMessActivity::class.java))
        }

        flowScopes()


        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        val dataToken = TOKEN.decodeJWT()
        val tipoPersonal = if (dataToken.size > 1) {
            val tokenR = dataToken[1].fromJsonToken()
            when (tokenR.rol) {
                US_ADMIN -> TipoPersonal.ADMIN
                US_PATROCINIO, US_FACILITADOR, US_TECNICO -> TipoPersonal.TECNICO
                else -> TipoPersonal.TECNICO
            }
        } else TipoPersonal.TECNICO
        if(tipoPersonal == TipoPersonal.ADMIN) {
            viewModel.getMessAll(token = "Bearer $TOKEN")
            binding?.recyclerMess?.adapter = adapterAll
        } else {
            viewModel.getMess(token = "Bearer $TOKEN")
            binding?.recyclerMess?.adapter = adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStop() {
        super.onStop()
        adapterAll?.arrayMess?.clear()
        adapterAll?.notifyDataSetChanged()
        adapter?.arrayMess?.clear()
        adapter?.notifyDataSetChanged()
    }

    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    val tipoUss = it.tokenTipoUs()
                    if(tipoUss == TipoPersonal.ADMIN) {
                        binding?.fabCrearMess?.show()
                    } else {
                        binding?.fabCrearMess?.hide()
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.mess.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arrayMess = it.data
                        adapter?.notifyItemRangeInserted(0, it.data.size)
                    }
                    is StateData.Error -> {

                    }
                    StateData.Loading -> {

                    }
                    StateData.None -> { }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.messAll.collect {
                when(it) {
                    is StateData.Success -> {
                        adapterAll?.arrayMess = it.data
                        adapterAll?.notifyItemRangeInserted(0, it.data.size)
                    }
                    is StateData.Error -> {

                    }
                    StateData.Loading -> {

                    }
                    StateData.None -> { }
                }
            }
        }
    }
}