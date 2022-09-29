package com.gotasoft.mosojkausay.model.entities.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class UnavailableResponse(
    var id: Int = 0,
    var local_partner_lookup: String = "",
    var community_lookup: String = "",
    var community_id: String = "",
    var village: String = "",
    var child_number: String = "",
    var participant_name: String = "",
    var participant_age: String = "",
    var sponsorship_status: String = "",
    var latitud: String? = "",
    var longitud: String? = "",
    var estado: String = "",
    var validacion: String = "",
    var responsable: String = "",
    var createdAt: String = "",
    var updatedAt: String? = "",
    var deletedAt: String? = "",
    var planilla_id: String = "",
    var comunidad_act: String? = "",
    var tecnico_campo: String? = ""
) : Parcelable
