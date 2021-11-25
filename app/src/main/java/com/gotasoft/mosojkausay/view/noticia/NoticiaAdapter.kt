package com.gotasoft.mosojkausay.view.noticia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.ItemNoticiaBinding
import com.gotasoft.mosojkausay.model.entities.response.NoticiaPublicadaResponse
import com.gotasoft.mosojkausay.utils.Tools

class NoticiaAdapter(var arrayNoticia: ArrayList<NoticiaPublicadaResponse> = arrayListOf()): RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>() {
    inner class NoticiaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoticiaBinding.bind(itemView)
        fun bind(noticia: NoticiaPublicadaResponse) {
            with(binding) {
                textTitItemNoticia.text = noticia.titulo
                textContItemNoticia.text = noticia.contenido
                val pub = "Publicado por ${noticia.personal.nombre_completo}"
                textPubliItemNoticia.text = pub
                val fecha = Tools.formatDateHour(noticia.momento_publicacion)
                textFechaItemNoticia.text = fecha
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder =
        NoticiaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_noticia, parent, false)
        )

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val not = arrayNoticia[position]
        holder.bind(not)
    }

    override fun getItemCount(): Int = arrayNoticia.size
}