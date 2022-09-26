package com.gotasoft.mosojkausay.model.entities.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class DfcResponse(
    var local_partner_lookup: String = "",
    var community_lookup: String = "",
    var community_id: String = "",
    var village: String = "",
    var child_number: String = "",
    var participant_name: String = "",
    var participant_age: String = "",
    var mcs_letter_type: String = "",
    var mcs_date: String = "",
    var mcs_id: String = "",
    var dfc_purpose: String = "",
    var dfc_amount: String = "",
    var dfc_currency: String = "",
    var donor_name: String = "",
    var donor_number: String = "",
    var alliance_name: String = "",
    var validacion: String = "",
    var responsable: String = "",
    var createdAt: String = "",
    var updatedAt: String? = "",
    var deletedAt: String? = "",
    var planilla_id: String = ""
) : Parcelable