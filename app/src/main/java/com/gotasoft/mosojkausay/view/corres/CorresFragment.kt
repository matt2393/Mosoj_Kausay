package com.gotasoft.mosojkausay.view.corres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.FragmentCorresBinding
import com.gotasoft.mosojkausay.view.corres.planilla.PlanillaFragment

class CorresFragment: Fragment() {
    private var binding: FragmentCorresBinding? = null
    companion object {
        val TAG = CorresFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCorresBinding.inflate(inflater, container, false)

        requireActivity().title = "Correspondencia"
        binding?.cardReply?.setOnClickListener {
            selectTypeCorres("reply")
        }
        binding?.cardWelcome?.setOnClickListener {
            selectTypeCorres("welcome")
        }
        binding?.cardDfc?.setOnClickListener {
            selectTypeCorres("dfc")
        }
        binding?.cardUnavailable?.setOnClickListener {
            selectTypeCorres("unavailable")
        }

        return binding?.root
    }
    private fun selectTypeCorres(tipo: String) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerCorres, PlanillaFragment.newInstance(tipo), PlanillaFragment.TAG)
            .addToBackStack(PlanillaFragment.TAG)
            .commit()
    }
}