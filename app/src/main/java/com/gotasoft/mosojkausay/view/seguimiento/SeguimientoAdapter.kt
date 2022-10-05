package com.gotasoft.mosojkausay.view.seguimiento

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.arrayMeses
import com.gotasoft.mosojkausay.databinding.ItemSeguimientoBinding
import com.gotasoft.mosojkausay.model.entities.response.SeguimientoResponse

class SeguimientoAdapter(var arraySeg: ArrayList<SeguimientoResponse> = arrayListOf(),
                         val edit:(SeguimientoResponse) -> Unit,
                         val activar:(SeguimientoResponse) -> Unit,
                         val desactivar:(SeguimientoResponse) -> Unit): RecyclerView.Adapter<SeguimientoAdapter.SeguimientoViewHolder>() {
    inner class SeguimientoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSeguimientoBinding.bind(itemView)
        fun bind(seg: SeguimientoResponse) {
            with(binding) {
                val posMes = seg.mes - 1
                textMesItemSeg.text = if (posMes > -1) arrayMeses[posMes] else ""
                textActItemSeg.text = seg.tipo
                textProgramadoItemSeg.text = seg.programado.toString()
                textEjecutadoItemSeg.text = seg.ejecutado.toString()
                if(seg.activo) {
                    fabActivarItemSeg.hide()
                    fabDesactivarItemSeg.show()
                } else {
                    fabActivarItemSeg.show()
                    fabDesactivarItemSeg.hide()
                }

                fabEditItemSeg.setOnClickListener {
                    edit(seg)
                }
                fabActivarItemSeg.setOnClickListener {
                    activar(seg)
                }
                fabDesactivarItemSeg.setOnClickListener {
                    desactivar(seg)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeguimientoViewHolder =
        SeguimientoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_seguimiento, parent, false)
        )

    override fun onBindViewHolder(holder: SeguimientoViewHolder, position: Int) {
        val se = arraySeg[position]
        holder.bind(se)
    }

    override fun getItemCount(): Int = arraySeg.size
}