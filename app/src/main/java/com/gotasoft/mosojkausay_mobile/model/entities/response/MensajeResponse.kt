package com.gotasoft.mosojkausay_mobile.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class MensajeResponse(
    var asunto: String = "",
    var contenido: String = "",
    var emisor: String = "",
    var fecha: String = ""
) : Parcelable