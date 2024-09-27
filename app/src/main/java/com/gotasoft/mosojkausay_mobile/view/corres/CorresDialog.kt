package com.gotasoft.mosojkausay_mobile.view.corres

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.gotasoft.mosojkausay_mobile.databinding.DialogCorresBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.DfcResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.ReplyResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.UnavailableResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.WelcomeResponse

class CorresDialog<R: Parcelable>: DialogFragment() {

    companion object {
        val TAG = CorresDialog::class.java.name
        private const val DATA = "DATA"
        fun <T: Parcelable> newInstance(data: T)= CorresDialog<T>().apply {
            arguments = Bundle().apply {
                putParcelable(DATA, data)
            }
        }
    }
    private var data: R? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = AlertDialog.Builder(requireContext())
        val binding = DialogCorresBinding.inflate(requireActivity().layoutInflater, null, false)
        alertDialog.setView(binding.root)

        alertDialog.setTitle("Correspondencia")
        alertDialog.setPositiveButton("Cerrar") { d, _ ->
            dismiss()
        }
        arguments?.let {
            data = it.getParcelable(DATA)
        }
        if(data!=null) {
            llenar(data!!, binding)
        } else {
            dismiss()
        }

        return alertDialog.create()
    }

    private fun llenar(data: R, binding: DialogCorresBinding) {
        when(data) {
            is ReplyResponse -> llenarReply(data, binding)
            is WelcomeResponse -> llenarWelcome(data, binding)
            is DfcResponse -> llenarDfc(data, binding)
            is UnavailableResponse -> llenarUnavailable(data, binding)
        }
    }
    private fun llenarReply(d: ReplyResponse, binding: DialogCorresBinding) {
        with(binding) {
            textIdDialogcorres.text = d.mcs_id
            textDateDialogcorres.text = d.mcs_date
            textLetterDialogcorres.text = d.mcs_letter_type
            textNomDialogcorres.text = d.participant_name
            textEdadDialogcorres.text = d.participant_age
            textComunityDialogcorres.text = d.community_lookup
            textComunityIdDialogcorres.text = d.community_id
            textChildNumberDialogcorres.text = d.child_number
            textDonorDialogcorres.text = d.donor_name
            textVillageDialogcorres.text = d.village
            textPartnerDialogcorres.text = d.local_partner_lookup
            textAllianceDialogcorres.text = d.alliance_name
            textResponsableDialogcorres.text = d.responsable
            textCommentsDialogcorres.text = d.comments
            textComunityActDialogcorres.text = d.comunidad_act ?: ""
            textTecnicoCampoDialogcorres.text = d.tecnico_campo ?: ""
        }
    }
    private fun llenarWelcome(d: WelcomeResponse, binding: DialogCorresBinding) {
        with(binding) {
            textIdDialogcorres.text = d.mcs_id
            textDateDialogcorres.text = d.mcs_date
            textLetterDialogcorres.text = d.mcs_letter_type
            textNomDialogcorres.text = d.participant_name
            textEdadDialogcorres.text = d.participant_age
            textComunityDialogcorres.text = d.community_lookup
            textComunityIdDialogcorres.text = d.community_id
            textChildNumberDialogcorres.text = d.child_number
            textDonorDialogcorres.text = d.donor_name
            textVillageDialogcorres.text = d.village
            textPartnerDialogcorres.text = d.local_partner_lookup
            textAllianceDialogcorres.text = d.alliance_name
            textResponsableDialogcorres.text = d.responsable
            textCommentsDialogcorres.visibility = View.GONE
            textTitCommentsDialogCorres.visibility = View.GONE
            textComunityActDialogcorres.text = d.comunidad_act ?: ""
            textTecnicoCampoDialogcorres.text = d.tecnico_campo ?: ""
        }
    }
    private fun llenarDfc(d: DfcResponse, binding: DialogCorresBinding) {
        with(binding) {
            textIdDialogcorres.text = d.mcs_id
            textDateDialogcorres.text = d.mcs_date
            textLetterDialogcorres.text = d.mcs_letter_type
            textNomDialogcorres.text = d.participant_name
            textEdadDialogcorres.text = d.participant_age
            textComunityDialogcorres.text = d.community_lookup
            textComunityIdDialogcorres.text = d.community_id
            textChildNumberDialogcorres.text = d.child_number
            textDonorDialogcorres.text = d.donor_name
            textVillageDialogcorres.text = d.village
            textPartnerDialogcorres.text = d.local_partner_lookup
            textAllianceDialogcorres.text = d.alliance_name
            textResponsableDialogcorres.text = d.responsable
            textCommentsDialogcorres.visibility = View.GONE
            textTitCommentsDialogCorres.visibility = View.GONE
            textComunityActDialogcorres.text = d.comunidad_act ?: ""
            textTecnicoCampoDialogcorres.text = d.tecnico_campo ?: ""
        }
    }
    private fun llenarUnavailable(d: UnavailableResponse, binding: DialogCorresBinding) {
        with(binding) {
            textTitIdDialogCorres.text = "ID:"
            textIdDialogcorres.text = d.id.toString()

            textTitDateDialogCorres.visibility = View.GONE
            textTitLetterDialogCorres.visibility = View.GONE
            textDateDialogcorres.visibility = View.GONE
            textLetterDialogcorres.visibility = View.GONE

            textNomDialogcorres.text = d.participant_name
            textEdadDialogcorres.text = d.participant_age
            textComunityDialogcorres.text = d.community_lookup
            textComunityIdDialogcorres.text = d.community_id
            textChildNumberDialogcorres.text = d.child_number
            textTitDonorDialogCorres.text = "Estado del Patrocinio: "
            textDonorDialogcorres.text = d.sponsorship_status
            textVillageDialogcorres.text = d.village
            textPartnerDialogcorres.text = d.local_partner_lookup
            textTitAllianceDialogCorres.text = "Estado:"
            textAllianceDialogcorres.text = d.estado
            textResponsableDialogcorres.text = d.responsable

            textCommentsDialogcorres.visibility = View.GONE
            textTitCommentsDialogCorres.visibility = View.GONE

            textComunityActDialogcorres.text = d.comunidad_act ?: ""
            textTecnicoCampoDialogcorres.text = d.tecnico_campo ?: ""
        }
    }

}