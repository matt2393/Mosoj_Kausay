package com.gotasoft.mosojkausay.view.login.init

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.URL_DOWNLOAD_IMAGE
import com.gotasoft.mosojkausay.databinding.ItemInitBinding
import com.gotasoft.mosojkausay.model.entities.response.SlideResponse

class InitAdapter(var arraySlides: ArrayList<SlideResponse> = arrayListOf()) :
    RecyclerView.Adapter<InitAdapter.InitViewHolder>() {
    inner class InitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemInitBinding.bind(itemView)
        fun bind(slideResponse: SlideResponse) {
            binding.imageItemInit.load(
                data = "${URL_DOWNLOAD_IMAGE}${slideResponse.id}",
                builder = {
                    listener { _, _ ->
                        binding.imageItemInit.visibility = View.VISIBLE
                        binding.lottieItemInit.visibility = View.GONE
                    }
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitViewHolder =
        InitViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_init, parent, false)
        )

    override fun onBindViewHolder(holder: InitViewHolder, position: Int) {
        val slide = arraySlides[position]
        holder.bind(slide)
    }

    override fun getItemCount(): Int = arraySlides.size
}