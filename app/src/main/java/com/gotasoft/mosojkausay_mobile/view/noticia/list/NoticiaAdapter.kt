package com.gotasoft.mosojkausay_mobile.view.noticia.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ItemNoticiaBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.NoticiaPublicadaResponse
import com.gotasoft.mosojkausay_mobile.utils.Tools

class NoticiaAdapter(
    var arrayNoticia: ArrayList<NoticiaPublicadaResponse> = arrayListOf(),
    val click: (NoticiaPublicadaResponse) -> Unit
): RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>() {
    inner class NoticiaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoticiaBinding.bind(itemView)
        fun bind(noticia: NoticiaPublicadaResponse) {
            with(binding) {
                textTitItemNoticia.text = noticia.titulo
                textContItemNoticia.text = noticia.contenido
                val pub = "Publicado por ${noticia.personal?.nombre_completo}"
                textPubliItemNoticia.text = pub
                val fecha = Tools.formatDateHour(noticia.momento_publicacion!!)
                textFechaItemNoticia.text = fecha
                root.setOnClickListener {
                    click.invoke(noticia)
                }
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