package com.gotasoft.mosojkausay.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gotasoft.mosojkausay.databinding.FragmentHomeTecnicoBinding
import com.gotasoft.mosojkausay.utils.TipoPersonal
import com.gotasoft.mosojkausay.view.mess.MessActivity
import com.gotasoft.mosojkausay.view.noticia.NoticiaActivity
import com.gotasoft.mosojkausay.view.participantes.ParticipantesActivity

class HomeTecnicoFragment: Fragment() {
    companion object {
        val TAG = HomeTecnicoFragment::class.java.name
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentHomeTecnicoBinding.inflate(inflater, container, false)
        bind.cardParticipantesTecnico.setOnClickListener {
            startActivity(
                Intent(requireContext(), ParticipantesActivity::class.java)
                    .putExtra(ParticipantesActivity.TIPO_PERSONAL, TipoPersonal.TECNICO.name)
            )
        }
        bind.cardMessTecnico.setOnClickListener {
            startActivity(
                Intent(requireContext(), MessActivity::class.java)
                    //.putExtra(ParticipantesActivity.TIPO_PERSONAL, TipoPersonal.TECNICO.name)
            )
        }
        bind.cardNotiTecnico.setOnClickListener {
            startActivity(
                Intent(requireContext(), NoticiaActivity::class.java)
                //.putExtra(ParticipantesActivity.TIPO_PERSONAL, TipoPersonal.TECNICO.name)
            )
        }
        return bind.root
    }
}