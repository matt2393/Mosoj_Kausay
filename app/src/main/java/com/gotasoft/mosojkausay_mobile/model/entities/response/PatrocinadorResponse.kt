package com.gotasoft.mosojkausay_mobile.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class PatrocinadorResponse(
    var asunto: String = "",
    var correo: String = "",
    var createdAt: String = "",
    var id: Int = 0,
    var mensaje: String = "",
    var nombre_completo: String = "",
    var updatedAt: String = ""
) : Parcelable