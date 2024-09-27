package com.gotasoft.mosojkausay_mobile.view.corres.list_corres

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ItemListCorresBinding

class ListCorresAdapter<T>(
    var arrayData: ArrayList<T> = arrayListOf(),
    val bind: (T ,ItemListCorresBinding) -> Unit,
    val ver: (T) -> Unit,
    val validar: (T) -> Unit,
    val pendiente: (T) -> Unit,
    val map: (T) -> Unit,
    val contact: (T) -> Unit
): RecyclerView.Adapter<ListCorresAdapter<T>.ListCorresViewHolder>() {
    inner class ListCorresViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListCorresBinding.bind(itemView)
        fun bind(t: T) {
            bind(t, binding)
            binding.fabVerItemCorres.setOnClickListener {
                ver(t)
            }
            binding.fabValidarItemCorres.setOnClickListener {
                validar(t)
            }
            binding.fabPendienteItemCorres.setOnClickListener {
                pendiente(t)
            }
            binding.fabMapItemCorres.setOnClickListener {
                map.invoke(t)
            }
            binding.fabContactoItemCorres.setOnClickListener {
                contact.invoke(t)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCorresViewHolder =
        ListCorresViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_corres, parent, false)
        )

    override fun onBindViewHolder(holder: ListCorresViewHolder, position: Int) {
        val d = arrayData[position]
        holder.bind(d)
    }

    override fun getItemCount(): Int = arrayData.size
}