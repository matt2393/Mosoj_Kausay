package com.gotasoft.mosojkausay.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gotasoft.mosojkausay.databinding.FragmentHomeAdminBinding
import com.gotasoft.mosojkausay.utils.TipoPersonal
import com.gotasoft.mosojkausay.view.mess.MessActivity
import com.gotasoft.mosojkausay.view.momentos_magicos.MMActivity
import com.gotasoft.mosojkausay.view.participantes.ParticipantesActivity

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
        return bind.root
    }
}