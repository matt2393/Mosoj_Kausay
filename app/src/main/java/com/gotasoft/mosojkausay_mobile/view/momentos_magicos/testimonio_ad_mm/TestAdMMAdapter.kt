package com.gotasoft.mosojkausay_mobile.view.momentos_magicos.testimonio_ad_mm

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ItemTesMmBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.TestimonioAdResponse
import com.gotasoft.mosojkausay_mobile.utils.inflateLayout

class TestAdMMAdapter(var arrayTes: ArrayList<TestimonioAdResponse> = arrayListOf()): RecyclerView.Adapter<TestAdMMAdapter.TestAdMMViewHolder>() {
    inner class TestAdMMViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTesMmBinding.bind(itemView)
        fun bind(tes: TestimonioAdResponse) {
            with(binding) {
                textPregItemTesMM.text = tes.pregunta
                textResItemTesMM.text = tes.testimonio
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdMMViewHolder =
        TestAdMMViewHolder(
            parent.inflateLayout(R.layout.item_tes_mm)
        )

    override fun onBindViewHolder(holder: TestAdMMViewHolder, position: Int) {
        val t = arrayTes[position]
        holder.bind(t)
    }

    override fun getItemCount(): Int = arrayTes.size
}