package com.gotasoft.mosojkausay_mobile.view.momentos_magicos

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ItemMmBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.MMResponse
import com.gotasoft.mosojkausay_mobile.utils.inflateLayout

class MMAdapter(var arrayMM: ArrayList<MMResponse> = arrayListOf(),
                val clickTestAd: (MMResponse) -> Unit,
                val clickFotos: (MMResponse) -> Unit): RecyclerView.Adapter<MMAdapter.MMViewHolder>() {
    inner class MMViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMmBinding.bind(itemView)
        fun bind(mmResponse: MMResponse) {
            with(binding) {
                textChildNumberItemMM.text = mmResponse.child_number
                textNombreItemMM.text = mmResponse.nombre_completo
                textModeloItemMM.text = mmResponse.modelo_programatico
                textTipoItemMM.text = mmResponse.tipo_momento_magico
                textResItemMM.text = mmResponse.resultado_intermedio
                fabTestItemMM.setOnClickListener {
                    clickTestAd(mmResponse)
                }
                fabFotosItemMM.setOnClickListener {
                    clickFotos(mmResponse)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MMViewHolder =
        MMViewHolder(
            parent.inflateLayout(R.layout.item_mm)
        )

    override fun onBindViewHolder(holder: MMViewHolder, position: Int) {
        val mm = arrayMM[position]
        holder.bind(mm)
    }

    override fun getItemCount(): Int = arrayMM.size
}