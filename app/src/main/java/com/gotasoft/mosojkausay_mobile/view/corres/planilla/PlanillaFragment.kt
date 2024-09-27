package com.gotasoft.mosojkausay_mobile.view.corres.planilla

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay_mobile.*
import com.gotasoft.mosojkausay_mobile.databinding.FragmentPlanillaBinding
import com.gotasoft.mosojkausay_mobile.utils.*
import com.gotasoft.mosojkausay_mobile.view.corres.list_corres.ListCorresFragment
import com.gotasoft.mosojkausay_mobile.view.load.LoadDialog

class PlanillaFragment: Fragment() {
    private val viewModel: PlanillaViewModel by viewModels()
    private var binding: FragmentPlanillaBinding? = null
    companion object {
        val TAG = PlanillaFragment::class.java.name
        private const val TIPO = "Tipo"
        fun newInstance(tipo: String) = PlanillaFragment().apply {
            arguments = Bundle().apply {
                putString(TIPO, tipo)
            }
        }
    }
    private var tipo: String = "reply"
    private var adapter: PlanillaAdapter? = null

    private var loadDialog: LoadDialog? = null
    private var token = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanillaBinding.inflate(inflater, container, false)
        arguments?.let {
            tipo = it.getString(TIPO, "reply")
        }
        adapter = PlanillaAdapter {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.containerCorres,
                    ListCorresFragment.newInstance(tipo, it.planilla_id), ListCorresFragment.TAG)
                .addToBackStack(ListCorresFragment.TAG)
                .commit()
        }

        requireActivity().title = tipo.uppercase()
        binding?.recyclerPlanilla?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerPlanilla?.adapter = adapter

        binding?.switchActivoPlanilla?.setOnClickListener {
            val act = getActivo()
            binding?.switchActivoPlanilla?.text = if(act == 0) "Inactivo" else "Activo"

            viewModel.getPlanilla(token, getActivo(), tipo)
        }

        binding?.swipePlanilla?.setOnRefreshListener {
            viewModel.getPlanilla(token, getActivo(), tipo)
        }

        flowScopes()

        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it != null) {
                    token = "Bearer $it"
                    when(it.tokenTipoUs()) {
                        TipoPersonal.PATROCINIO -> {
                            binding?.switchActivoPlanilla?.visibility = View.VISIBLE
                        }
                        else -> {
                            binding?.switchActivoPlanilla?.visibility = View.GONE
                        }
                    }

                    viewModel.getPlanilla(token, getActivo(), tipo)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.planilla.collect {
                when(it) {
                    is StateData.Success -> {
                        binding?.swipePlanilla?.isRefreshing = false
                        adapter?.arrayPlanilla = it.data
                        adapter?.notifyDataSetChanged()
                    }
                    is StateData.Error -> {
                        binding?.swipePlanilla?.isRefreshing = false
                        Log.e("ErrorPlanilla", it.toString())
                    }
                    StateData.Loading -> {
                        if (loadDialog == null) {
                            loadDialog = LoadDialog()
                            loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                        }
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
    private fun getActivo(): Int =
        if(binding?.switchActivoPlanilla?.isChecked != false) 1 else 0

}