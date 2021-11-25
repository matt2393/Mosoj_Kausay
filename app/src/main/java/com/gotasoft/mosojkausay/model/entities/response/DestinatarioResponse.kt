package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
@Parcelize
data class DestinatarioResponse(
    @Json(name = "Personal") var personal: Personal = Personal(),
    var leido: Boolean = false
) : Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Personal(
        var nombre_completo: String = ""
    ) : Parcelable
}