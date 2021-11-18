package com.gotasoft.mosojkausay.model.entities.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParticipanteRequest(
    var child_number: String = "",
    var curso: String? = "",
    var escuela: String? = "",
    var fecha_nacimiento: String? = "",
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
    var village: String? = ""
)