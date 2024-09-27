package com.gotasoft.mosojkausay_mobile.view.mess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ItemMessBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.MessageResponse

class AllMessAdapter(var arrayMess: ArrayList<MessageResponse> = arrayListOf(),
                     val clickDest: (MessageResponse) -> Unit): RecyclerView.Adapter<AllMessAdapter.AllMessViewHolder>() {
    inner class AllMessViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMessBinding.bind(itemView)
        fun bind(mensaje: MessageResponse) {
            with(binding) {
                textAsuntoItemMess.text = mensaje.asunto
                textContenidoItemMess.text = mensaje.contenido
                val emisor = "${mensaje.personal?.nombre_completo}"
                textEmisorItemMess.text = emisor
                fabDestItemMess.show()
                fabDestItemMess.setOnClickListener {
                    clickDest(mensaje)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMessViewHolder =
        AllMessViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mess, parent, false)
        )

    override fun onBindViewHolder(holder: AllMessViewHolder, position: Int) {
        val mess = arrayMess[position]
        holder.bind(mess)
    }

    override fun getItemCount(): Int  = arrayMess.size
}