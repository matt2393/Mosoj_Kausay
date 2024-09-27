package com.gotasoft.mosojkausay_mobile.view.corres.planilla

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ItemPlanillaBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.PlanillaResponse
import com.gotasoft.mosojkausay_mobile.utils.Tools
import java.util.*
import kotlin.collections.ArrayList

class PlanillaAdapter(var arrayPlanilla: ArrayList<PlanillaResponse> = arrayListOf(),
                      val clickItem: (PlanillaResponse) -> Unit): RecyclerView.Adapter<PlanillaAdapter.PlanillaViewHolder>() {

    inner class PlanillaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPlanillaBinding.bind(itemView)
        fun bind(planilla: PlanillaResponse) {
            with(binding) {
                textTitItemPlanilla.text = planilla.planilla_id
                textInicioItemPlanilla.text = Tools.formatDate(planilla.inicio)
                textFinItemPlanilla.text = Tools.formatDate(planilla.fin)
                root.setOnClickListener {
                    clickItem(planilla)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanillaViewHolder =
        PlanillaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_planilla, parent, false)
        )

    override fun onBindViewHolder(holder: PlanillaViewHolder, position: Int) {
        val pl = arrayPlanilla[position]
        holder.bind(pl)
    }

    override fun getItemCount(): Int = arrayPlanilla.size
}