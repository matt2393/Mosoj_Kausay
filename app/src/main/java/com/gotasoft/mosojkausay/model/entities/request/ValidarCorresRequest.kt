package com.gotasoft.mosojkausay.model.entities.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ValidarCorresRequest(var tipo: String = "", var validacion: String = "")