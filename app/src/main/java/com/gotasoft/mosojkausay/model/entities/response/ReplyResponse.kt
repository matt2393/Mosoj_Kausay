package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class ReplyResponse(
    var alliance_name: String = "",
    var child_number: String = "",
    var comments: String? = "",
    var community_id: String = "",
    var community_lookup: String = "",
    var createdAt: String = "",
    var deletedAt: String? = "",
    var donor_name: String = "",
    var local_partner_lookup: String = "",
    var mcs_date: String = "",
    var mcs_id: String = "",
    var mcs_letter_type: String = "",
    var participant_age: String = "",
    var participant_name: String = "",
    var planilla_id: String = "",
    var responsable: String = "",
    var updatedAt: String? = "",
    var validacion: String = "",
    var village: String = "",
    var comunidad_act: String? = "",
    var tecnico_campo: String? = ""
) : Parcelable