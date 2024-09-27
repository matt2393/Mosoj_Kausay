package com.gotasoft.mosojkausay_mobile.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@Parcelize
@JsonClass(generateAdapter = true)
data class ParticipanteResponse(
    var edad: Int = 0,
    var child_number: String = "",
    var comunidad: String = "",
    var createdAt: String = "",
    var curso: String? = "",
    var deletedAt: String? = null,
    var escuela: String? = "",
    var estado_actual: String? = "",
    var fecha_nacimiento: String? = "",
    var foto: String? = null,
    var foto_domicilio: String? = null,
    var gender: String? = "",
    var hijos: String? = "",
    var hijos_afiliados: String? = "",
    var id_village: Int = 0,
    var latitud: String? = "",
    var longitud: String? = "",
    var madre: String? = "",
    var madre_edad: String? = "",
    var madre_ocupacion: String? = "",
    var madre_telefono: String? = "",
    var madre_trabajo: String? = "",
    var nivel: String? = "",
    var nombre_completo: String? = "",
    var padre: String? = "",
    var padre_edad: String? = "",
    var padre_ocupacion: String? = "",
    var padre_telefono: String? = "",
    var padre_trabajo: String? = "",
    var referencia_familiar: String? = "",
    var referencia_familiar_telefono: String? = "",
    var turno: String? = "",
    var updatedAt: String? = "",
    var usuarioId: String = "",
    var village: String? = ""
) : Parcelable