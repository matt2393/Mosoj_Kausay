package com.gotasoft.mosojkausay_mobile.view.villa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ItemVillaBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.VillaResponse

class VillaAdapter(var arrayVillas: ArrayList<VillaResponse> = arrayListOf(),
                   val select: (villa: VillaResponse) -> Unit): RecyclerView.Adapter<VillaAdapter.VillaViewHolder>() {
    inner class VillaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemVillaBinding.bind(itemView)
        fun bind(villa: VillaResponse) {
            with(binding.buttonItemSocio) {
                text = villa.village
                setOnClickListener {
                    select(villa)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VillaViewHolder =
        VillaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_villa, parent, false)
        )

    override fun onBindViewHolder(holder: VillaViewHolder, position: Int) {
        val villa = arrayVillas[position]
        holder.bind(villa)
    }

    override fun getItemCount(): Int = arrayVillas.size
}