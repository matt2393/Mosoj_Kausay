package com.gotasoft.mosojkausay.view.participantes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.ItemParticipantesBinding
import com.gotasoft.mosojkausay.model.entities.response.PartTotales

class ParticipantesAdapter(var listTotales: MutableList<PartTotales> = mutableListOf()): RecyclerView.Adapter<ParticipantesAdapter.ParticipantesViewHolder>() {
    inner class ParticipantesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemParticipantesBinding.bind(itemView)
        fun bind(part: PartTotales) {
            with(binding) {
                textTitItemParticipantes.text = part.mes_nombre
                textNumNuevoItemPart.text = part.nuevos.toString()
                textNumMigracionItemPart.text = part.migrados.toString()
                textNumTotalItemPart.text = part.total.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantesViewHolder =
        ParticipantesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_participantes, parent, false)
        )

    override fun onBindViewHolder(holder: ParticipantesViewHolder, position: Int) {
        val part = listTotales[position]
        holder.bind(part)
    }

    override fun getItemCount(): Int = listTotales.size
}