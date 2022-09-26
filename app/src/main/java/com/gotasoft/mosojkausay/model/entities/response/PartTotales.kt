package com.gotasoft.mosojkausay.model.entities.response

data class PartTotales(
    val gestion: Int,
    val mes_nombre: String,
    val nuevos: Int,
    val migrados: Int,
    val total: Int
)
