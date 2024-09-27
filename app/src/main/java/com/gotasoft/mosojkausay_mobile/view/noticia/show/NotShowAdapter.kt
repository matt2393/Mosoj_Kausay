package com.gotasoft.mosojkausay_mobile.view.noticia.show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.URL_DOWNLOAD_FILE
import com.gotasoft.mosojkausay_mobile.databinding.ItemMultiDocBinding
import com.gotasoft.mosojkausay_mobile.databinding.ItemMultiEnlaceBinding
import com.gotasoft.mosojkausay_mobile.databinding.ItemMultiImageBinding
import com.gotasoft.mosojkausay_mobile.databinding.ItemMultiVideoBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.NoticiaShowRes

class NotShowAdapter(
    var arrayMulti: ArrayList<NoticiaShowRes.Multimedia> = arrayListOf(),
    val click: (NoticiaShowRes.Multimedia) -> Unit
): RecyclerView.Adapter<NotShowAdapter.NotShowViewHolder>() {

    abstract inner class NotShowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind(mul: NoticiaShowRes.Multimedia)
    }
    inner class NotShowImageViewHolder(itemView: View): NotShowViewHolder(itemView) {
        private val binding = ItemMultiImageBinding.bind(itemView)
        override fun bind(mul: NoticiaShowRes.Multimedia) {
            binding.imageMulti.load("$URL_DOWNLOAD_FILE${mul.id }")
            binding.textMultiImage.text = mul.titulo
            binding.root.setOnClickListener {
                click.invoke(mul)
            }
        }
    }
    inner class NotShowDocViewHolder(itemView: View): NotShowViewHolder(itemView) {
        private val binding = ItemMultiDocBinding.bind(itemView)
        override fun bind(mul: NoticiaShowRes.Multimedia) {
            binding.textItemMultiDoc.text = mul.titulo
            binding.root.setOnClickListener {
                click.invoke(mul)
            }
        }
    }
    inner class NotShowVideoViewHolder(itemView: View): NotShowViewHolder(itemView) {
        private val binding = ItemMultiVideoBinding.bind(itemView)
        override fun bind(mul: NoticiaShowRes.Multimedia) {
            //binding.textMultiVideo.text = mul.titulo
            binding.textItemMultiVideo.text = mul.titulo
            binding.root.setOnClickListener {
                click.invoke(mul)
            }
        }
    }
    inner class NotShowEnlaceViewHolder(itemView: View): NotShowViewHolder(itemView) {
        private val binding = ItemMultiEnlaceBinding.bind(itemView)
        override fun bind(mul: NoticiaShowRes.Multimedia) {
            binding.textItemMultiEnlace.text = mul.titulo
            binding.root.setOnClickListener {
                click.invoke(mul)
            }
        }
    }
    inner class NotShowDefaultViewHolder(itemView: View): NotShowViewHolder(itemView) {
        override fun bind(mul: NoticiaShowRes.Multimedia) {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(arrayMulti[position].multimediaTipo) {
            "video" -> 1
            "documento" -> 2
            "imagen" -> 3
            "enlace" -> 4
            else -> 0
        }
    }

    override fun getItemCount(): Int = arrayMulti.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotShowViewHolder =
        when(viewType) {
            1 -> NotShowVideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_multi_video, parent, false))
            2 -> NotShowDocViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_multi_doc, parent, false))
            3 -> NotShowImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_multi_image, parent, false))
            4 -> NotShowEnlaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_multi_enlace, parent, false))
            else -> NotShowDefaultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_multi_video, parent, false))
        }

    override fun onBindViewHolder(holder: NotShowViewHolder, position: Int) {
        val m = arrayMulti[position]
        holder.bind(m)
    }
}