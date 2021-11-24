package com.gotasoft.mosojkausay.view.mess.crear_mess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.ItemPersonalBinding
import com.gotasoft.mosojkausay.model.entities.response.PersonalResponse

class PersonalAdapter(var arrayPersonal: ArrayList<PersonalResponse> = arrayListOf()): RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder>() {
    inner class PersonalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPersonalBinding.bind(itemView)
        fun bind(personal: PersonalResponse, pos: Int) {
            binding.textNomItemPersonal.text = personal.username
            binding.checkItemPersonal.isChecked = personal.selected
            binding.root.setOnClickListener {
                personal.selected = !personal.selected
                arrayPersonal[pos] = personal
                notifyItemChanged(pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalViewHolder =
        PersonalViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_personal, parent, false)
        )

    override fun onBindViewHolder(holder: PersonalViewHolder, position: Int) {
        val per = arrayPersonal[position]
        holder.bind(per, position)
    }

    override fun getItemCount(): Int = arrayPersonal.size
}