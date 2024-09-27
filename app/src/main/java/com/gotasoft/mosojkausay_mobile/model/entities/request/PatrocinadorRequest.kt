package com.gotasoft.mosojkausay_mobile.model.entities.request


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class PatrocinadorRequest(
    var asunto: String = "",
    var correo: String = "",
    var mensaje: String = "",
    var nombre_completo: String = ""
) : Parcelable