package com.gotasoft.mosojkausay.model.entities.request


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class SeguimientoEditRequest(
    var activo: Boolean = false,
    var ejecutado: Int = 0,
    var gestion: Int = 0,
    var mes: Int = 0,
    var modelo_programatico: String = "",
    var programado: Int = 0,
    var tipo: String = ""
) : Parcelable