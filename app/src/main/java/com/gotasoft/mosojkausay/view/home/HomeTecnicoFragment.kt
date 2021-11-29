package com.gotasoft.mosojkausay.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay.databinding.FragmentHomeTecnicoBinding
import com.gotasoft.mosojkausay.utils.TipoPersonal
import com.gotasoft.mosojkausay.utils.getToken
import com.gotasoft.mosojkausay.utils.tokenTipoUs
import com.gotasoft.mosojkausay.utils.tokenTipoUsApi
import com.gotasoft.mosojkausay.view.corres.CorresActivity
import com.gotasoft.mosojkausay.view.mess.MessActivity
import com.gotasoft.mosojkausay.view.momentos_magicos.MMActivity
import com.gotasoft.mosojkausay.view.noticia.NoticiaActivity
import com.gotasoft.mosojkausay.view.participantes.ParticipantesActivity
import kotlinx.coroutines.flow.collect

class HomeTecnicoFragment: Fragment() {
    companion object {
        val TAG = HomeTecnicoFragment::class.java.name
    }
    private var bind: FragmentHomeTecnicoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentHomeTecnicoBinding.inflate(inflater, container, false)
        bind?.cardParticipantesTecnico?.setOnClickListener {
            startActivity(
                Intent(requireContext(), ParticipantesActivity::class.java)
                    .putExtra(ParticipantesActivity.TIPO_PERSONAL, TipoPersonal.TECNICO.name)
            )
        }
        bind?.cardMessTecnico?.setOnClickListener {
            startActivity(
                Intent(requireContext(), MessActivity::class.java)
                    //.putExtra(ParticipantesActivity.TIPO_PERSONAL, TipoPersonal.TECNICO.name)
            )
        }
        bind?.cardNotiTecnico?.setOnClickListener {
            startActivity(
                Intent(requireContext(), NoticiaActivity::class.java)
                //.putExtra(ParticipantesActivity.TIPO_PERSONAL, TipoPersonal.TECNICO.name)
            )
        }
        bind?.cardCorresTecnico?.setOnClickListener {
            startActivity(
                Intent(requireContext(), CorresActivity::class.java)
                //.putExtra(ParticipantesActivity.TIPO_PERSONAL, TipoPersonal.TECNICO.name)
            )
        }
        bind?.cardMMTecnico?.setOnClickListener {
            startActivity(
                Intent(requireContext(), MMActivity::class.java)
            )
        }
        flowScope()
        return bind?.root
    }

    private fun flowScope() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if (it!=null) {
                    when(it.tokenTipoUs()) {
                        TipoPersonal.PARTICIPANTE -> {
                            bind?.cardMessTecnico?.visibility = View.GONE
                            bind?.cardParticipantesTecnico?.visibility = View.GONE
                            bind?.cardCorresTecnico?.visibility = View.GONE
                            bind?.cardMMTecnico?.visibility = View.GONE
                        }
                        TipoPersonal.TECNICO -> {

                        }
                        TipoPersonal.PATROCINIO -> {

                        }
                        TipoPersonal.FACILITADOR -> {
                            bind?.cardCorresTecnico?.visibility = View.GONE
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}