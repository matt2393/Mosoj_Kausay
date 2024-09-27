package com.gotasoft.mosojkausay_mobile.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gotasoft.mosojkausay_mobile.databinding.FragmentHomeAdminBinding
import com.gotasoft.mosojkausay_mobile.utils.TipoPersonal
import com.gotasoft.mosojkausay_mobile.view.mess.MessActivity
import com.gotasoft.mosojkausay_mobile.view.momentos_magicos.MMActivity
import com.gotasoft.mosojkausay_mobile.view.participantes.ParticipantesActivity
import com.gotasoft.mosojkausay_mobile.view.seguimiento.SeguimientoActivity

class HomeAdminFragment: Fragment() {

    companion object {
        val TAG = HomeAdminFragment::class.java.name
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentHomeAdminBinding.inflate(inflater, container, false)
        bind.cardMessPersonal.setOnClickListener {
            startActivity(Intent(requireContext(), MessActivity::class.java))
        }
        bind.cardParticipantesPersonal.setOnClickListener {
            startActivity(
                Intent(requireContext(), ParticipantesActivity::class.java)
                    .putExtra(ParticipantesActivity.TIPO_PERSONAL, TipoPersonal.ADMIN.name)
            )
        }
        bind.cardMMPersonal.setOnClickListener {
            startActivity(
                Intent(requireContext(), MMActivity::class.java)
            )
        }
        bind.cardSegPersonal.setOnClickListener {
            startActivity(
                Intent(requireContext(), SeguimientoActivity::class.java)
            )
        }
        return bind.root
    }
}