package com.gotasoft.mosojkausay.view.participantes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.ItemParticipantesBinding

class ParticipantesAdapter: RecyclerView.Adapter<ParticipantesAdapter.ParticipantesViewHolder>() {
    inner class ParticipantesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val bind = ItemParticipantesBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantesViewHolder =
        ParticipantesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_participantes, parent, false)
        )

    override fun onBindViewHolder(holder: ParticipantesViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 10
}