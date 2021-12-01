package com.gotasoft.mosojkausay.view.mis_seguimientos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.arrayMeses
import com.gotasoft.mosojkausay.databinding.ItemMisSegBinding
import com.gotasoft.mosojkausay.databinding.ItemSeguimientoBinding
import com.gotasoft.mosojkausay.model.entities.response.SeguimientoResponse
import com.gotasoft.mosojkausay.view.seguimiento.SeguimientoAdapter

class MisSegAdapter(var arraySeg: ArrayList<SeguimientoResponse> = arrayListOf(),
                    val guardar:(SeguimientoResponse, String) -> Unit): RecyclerView.Adapter<MisSegAdapter.MisSegViewHolder>() {
    inner class MisSegViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMisSegBinding.bind(itemView)
        fun bind(seg: SeguimientoResponse) {
            with(binding) {
                textMesItemMisSeg.text = arrayMeses[seg.mes]
                textActItemMisSeg.text = seg.tipo
                textProgramadoItemMisSeg.text = seg.programado.toString()
                editEjecutadoItemMisSeg.setText(seg.ejecutado.toString())
                fabGuardarItemMisSeg.setOnClickListener {
                    val ejec = editEjecutadoItemMisSeg.text.toString()
                    if(ejec.isNotEmpty()) {
                        guardar(seg, ejec)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisSegViewHolder =
        MisSegViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mis_seg, parent, false)
        )

    override fun onBindViewHolder(holder: MisSegViewHolder, position: Int) {
        val se = arraySeg[position]
        holder.bind(se)
    }

    override fun getItemCount(): Int = arraySeg.size
}