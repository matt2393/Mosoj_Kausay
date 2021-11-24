package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class PersonalResponse(
    @Json(name = "Personal")
    var personal: Personal = Personal(),
    var estado: String = "",
    var rol: String = "",
    var ultimoLogin: String? = "",
    var username: String = "",
    var selected: Boolean = false
) : Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Personal(
        var celular: String? = null,
        var ci: String = "",
        var correo: String? = null,
        var nombre_completo: String = ""
    ) : Parcelable
}