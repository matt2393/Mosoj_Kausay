package com.gotasoft.mosojkausay.view.mis_seguimientos

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.arrayGestion
import com.gotasoft.mosojkausay.arrayTipoSeg
import com.gotasoft.mosojkausay.databinding.FragmentMisSeguimientosBinding
import com.gotasoft.mosojkausay.databinding.FragmentSeguimientoBinding
import com.gotasoft.mosojkausay.utils.getToken
import com.gotasoft.mosojkausay.view.load.LoadDialog
import com.gotasoft.mosojkausay.view.seguimiento.SeguimientoAdapter
import com.gotasoft.mosojkausay.view.seguimiento.SeguimientoFragment
import com.gotasoft.mosojkausay.view.seguimiento.SeguimientoViewModel
import kotlinx.coroutines.flow.collect

class MisSegFragment: Fragment() {

    private val viewModel: MisSegViewModel by viewModels()
    private var binding: FragmentMisSeguimientosBinding? = null
    companion object {
        val TAG = MisSegFragment::class.java.name
    }

    private var token = ""
    private var adapter: MisSegAdapter? = null
    private var posTipo = 0

    private var loadDialog: LoadDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisSeguimientosBinding.inflate(inflater, container, false)
        adapter = MisSegAdapter(guardar = { seg, ejec ->
            seg.ejecutado = ejec.toInt()
            viewModel.getEditSegs(token, seg.id, seg.toSegEditReq())
        })
        with(binding!!) {
            recyclerMisSeg.layoutManager = LinearLayoutManager(requireContext())
            recyclerMisSeg.adapter = adapter
            val adapterTipo = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, arrayTipoSeg)
            autocompleteTipoMisSeg.setAdapter(adapterTipo)
            autocompleteTipoMisSeg.setText(arrayTipoSeg[0], false)

            autocompleteTipoMisSeg.setOnItemClickListener { _, _, position, _ ->
                posTipo = position
                viewModel.getMisSegs(token, arrayTipoSeg[posTipo])
            }
            swipeMisSeg.setOnRefreshListener {
                viewModel.getMisSegs(token, arrayTipoSeg[posTipo])
            }
        }
        flowScopes()
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    token = "Bearer $it"
                    viewModel.getMisSegs(token, arrayTipoSeg[0])
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.segs.collect {
                when(it) {
                    is StateData.Success -> {
                        adapter?.arraySeg = it.data
                        adapter?.notifyDataSetChanged()
                        binding?.swipeMisSeg?.isRefreshing = false
                    }
                    is StateData.Error -> {
                        binding?.swipeMisSeg?.isRefreshing = false
                        Log.e("MisSegError", it.toString())
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.isCancelable = false
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    StateData.None -> {
                        if(loadDialog != null) {
                            loadDialog?.dismiss()
                            loadDialog = null
                        }

                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.editSegs.collect {
                when(it) {
                    is StateData.Success -> {
                        viewModel.getMisSegs(token, arrayTipoSeg[0])
                        Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    }
                    is StateData.Error -> {
                        Log.e("MisSegError", it.toString())
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.isCancelable = false
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    StateData.None -> {
                        if(loadDialog != null) {
                            loadDialog?.dismiss()
                            loadDialog = null
                        }

                    }
                }
            }
        }
    }
}