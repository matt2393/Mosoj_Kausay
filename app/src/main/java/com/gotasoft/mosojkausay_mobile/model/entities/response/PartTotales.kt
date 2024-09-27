package com.gotasoft.mosojkausay_mobile.model.entities.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PartTotales(
    val gestion: Int,
    val mes_nombre: String,
    val nuevos: Int,
    val migrados: Int,
    val total: Int
)
