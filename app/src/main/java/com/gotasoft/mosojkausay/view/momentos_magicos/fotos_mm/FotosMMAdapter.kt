package com.gotasoft.mosojkausay.view.momentos_magicos.fotos_mm

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.URL_DOWNLOAD_PHOTO_MM
import com.gotasoft.mosojkausay.databinding.ItemFotoMmBinding
import com.gotasoft.mosojkausay.model.entities.response.FotoMMResponse
import com.gotasoft.mosojkausay.utils.inflateLayout

class FotosMMAdapter(var arrayFotos: ArrayList<FotoMMResponse> = arrayListOf()): RecyclerView.Adapter<FotosMMAdapter.FotosMMViewHolder>() {
    inner class FotosMMViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFotoMmBinding.bind(itemView)
        fun bind(foto: FotoMMResponse) {
            with(binding) {
                textItemFotoMM.text = foto.descripcion
                imageItemFotoMM.load("$URL_DOWNLOAD_PHOTO_MM${foto.id}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotosMMViewHolder =
        FotosMMViewHolder(
            parent.inflateLayout(R.layout.item_foto_mm)
        )

    override fun onBindViewHolder(holder: FotosMMViewHolder, position: Int) {
        val f = arrayFotos[position]
        holder.bind(f)
    }

    override fun getItemCount(): Int = arrayFotos.size
}