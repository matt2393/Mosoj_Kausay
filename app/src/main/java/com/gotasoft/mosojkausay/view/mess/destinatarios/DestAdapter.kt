package com.gotasoft.mosojkausay.view.mess.destinatarios

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.ItemDestinatarioBinding
import com.gotasoft.mosojkausay.model.entities.response.DestinatarioResponse

class DestAdapter(var arrayDest: ArrayList<DestinatarioResponse> = arrayListOf()): RecyclerView.Adapter<DestAdapter.DestViewHolder>() {
    inner class DestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDestinatarioBinding.bind(itemView)
        fun bind(dest: DestinatarioResponse) {
            with(binding) {
                textNomItemDest.text = dest.personal.nombre_completo
                if (dest.leido) {
                    imageCheckItemDest.setImageResource(R.drawable.ic_check_double)
                    imageCheckItemDest.setColorFilter(Color.parseColor("#FF018786"))
                } else {
                    imageCheckItemDest.setImageResource(R.drawable.ic_check)
                    imageCheckItemDest.setColorFilter(Color.parseColor("#5C5C5C"))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestViewHolder =
        DestViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_destinatario, parent, false)
        )

    override fun onBindViewHolder(holder: DestViewHolder, position: Int) {
        val d = arrayDest[position]
        holder.bind(d)
    }

    override fun getItemCount(): Int = arrayDest.size

}