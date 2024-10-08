package com.gotasoft.mosojkausay_mobile.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class MessageResponse(
    @Json(name = "Personal")
    var personal: Personal? = Personal(),
    var ci: String? = "",
    var contenido: String = "",
    var createdAt: String = "",
    var id: Int = 0,
    var publicado: Boolean = false,
    var titulo: String = "",
    var asunto: String = "",
    var updatedAt: String? = ""
) : Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Personal(
        var nombre_completo: String = ""
    ) : Parcelable
}