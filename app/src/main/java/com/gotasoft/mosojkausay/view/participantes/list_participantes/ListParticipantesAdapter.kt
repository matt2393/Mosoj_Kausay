package com.gotasoft.mosojkausay.view.participantes.list_participantes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.ItemListParticipanteBinding
import com.gotasoft.mosojkausay.model.entities.response.ParticipanteResponse

class ListParticipantesAdapter(var arrayParticipantes: ArrayList<ParticipanteResponse> = arrayListOf(),
                               val loc: (ParticipanteResponse) -> Unit,
                               val edit: (ParticipanteResponse) -> Unit,
                               val marcar: (ParticipanteResponse) -> Unit,
                               val perfil: (ParticipanteResponse) -> Unit): RecyclerView.Adapter<ListParticipantesAdapter.ListParticipantesViewHolder>() {
    inner class ListParticipantesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListParticipanteBinding.bind(itemView)
        fun bind(participante: ParticipanteResponse) {
            binding.textChildItemParticipante.text = participante.child_number
            binding.textNombreItemParticipante.text = participante.nombre_completo
            binding.textEdadItemParticipante.text = participante.edad.toString()
            binding.fabEditItemParticipante.setOnClickListener {
                edit(participante)
            }
            binding.fabDataItemParticipante.setOnClickListener {
                perfil(participante)
            }
            with(binding.fabLocationItemParticipante) {
                if (participante.latitud.isNullOrBlank() || participante.longitud.isNullOrEmpty()) {
                    hide()
                } else {
                    show()
                }
                if(participante.latitud!=null) {
                    if(participante.latitud!!.filter { it == '.' }.count() > 1) {
                        hide()
                    } else {
                        show()
                    }
                }
                if(participante.longitud!=null) {
                    if(participante.longitud!!.filter { it == '.' }.count() > 1) {
                        hide()
                    } else {
                        show()
                    }
                }

                setOnClickListener {
                    loc(participante)
                }
            }
            with(binding.fabPhoneItemParticipante) {
                if(participante.referencia_familiar_telefono.isNullOrEmpty() &&
                    participante.madre_telefono.isNullOrEmpty() &&
                    participante.padre_telefono.isNullOrEmpty()) {
                    hide()
                } else {
                    show()
                }
                setOnClickListener {
                    marcar(participante)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListParticipantesViewHolder =
        ListParticipantesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_participante, parent, false)
        )

    override fun onBindViewHolder(holder: ListParticipantesViewHolder, position: Int) {
        val part = arrayParticipantes[position]
        holder.bind(part)
    }

    override fun getItemCount(): Int = arrayParticipantes.size
}