package com.gotasoft.mosojkausay_mobile.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay_mobile.databinding.FragmentHomeTecnicoBinding
import com.gotasoft.mosojkausay_mobile.utils.TipoPersonal
import com.gotasoft.mosojkausay_mobile.utils.getToken
import com.gotasoft.mosojkausay_mobile.utils.tokenTipoUs
import com.gotasoft.mosojkausay_mobile.view.corres.CorresActivity
import com.gotasoft.mosojkausay_mobile.view.mess.MessActivity
import com.gotasoft.mosojkausay_mobile.view.mis_seguimientos.MisSegActivity
import com.gotasoft.mosojkausay_mobile.view.momentos_magicos.MMActivity
import com.gotasoft.mosojkausay_mobile.view.noticia.NoticiaActivity
import com.gotasoft.mosojkausay_mobile.view.participantes.ParticipantesActivity

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
        bind?.cardSegTecnico?.setOnClickListener {
            startActivity(
                Intent(requireContext(), MisSegActivity::class.java)
            )
        }
        bind?.cardConteoTecnico?.setOnClickListener {
            startActivity(
                Intent(requireContext(), ParticipantesActivity::class.java)
                    .putExtra(ParticipantesActivity.TIPO_PERSONAL, TipoPersonal.ADMIN.name)
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
                            bind?.cardSegTecnico?.visibility = View.VISIBLE
                        }
                        TipoPersonal.FINANZAS -> {
                            bind?.cardMessTecnico?.visibility = View.VISIBLE
                            bind?.cardParticipantesTecnico?.visibility = View.GONE
                            bind?.cardCorresTecnico?.visibility = View.GONE
                            bind?.cardMMTecnico?.visibility = View.GONE
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}