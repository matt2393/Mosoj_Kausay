package com.gotasoft.mosojkausay.model.entities.request


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class MMRequest(
    var child_number: String = "",
    var consentimiento: Boolean = false,
    var edad: Int = 0,
    var fecha_envio: String = "",
    var modelo_programatico: String = "",
    var nombre_completo: String = "",
    var ocupacion: String = "",
    var referencia_familiar: String = "",
    var resultado_intermedio: String = "",
    var testimonio_1: String? = null,
    var testimonio_2: String? = null,
    var testimonio_3: String? = null,
    var testimonio_4: String? = null,
    var tipo_momento_magico: String = ""
) : Parcelable