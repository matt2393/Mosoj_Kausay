package com.gotasoft.mosojkausay_mobile.view.mis_seguimientos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.arrayMeses
import com.gotasoft.mosojkausay_mobile.databinding.ItemMisSegBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.SeguimientoResponse

class MisSegAdapter(var arraySeg: ArrayList<SeguimientoResponse> = arrayListOf(),
                    val guardar:(SeguimientoResponse, String) -> Unit): RecyclerView.Adapter<MisSegAdapter.MisSegViewHolder>() {
    inner class MisSegViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMisSegBinding.bind(itemView)
        fun bind(seg: SeguimientoResponse) {
            with(binding) {
                val posMes = seg.mes - 1
                textMesItemMisSeg.text = "${if (posMes > -1) arrayMeses[posMes] else ""} - ${seg.gestion}"
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