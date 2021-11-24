package com.gotasoft.mosojkausay.model.entities.request


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class MessageRequest(
    var asunto: String = "",
    var contenido: String = "",
    var destinatarios: List<Destinatario> = listOf()
) : Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Destinatario(
        var ci: String = ""
    ) : Parcelable
}