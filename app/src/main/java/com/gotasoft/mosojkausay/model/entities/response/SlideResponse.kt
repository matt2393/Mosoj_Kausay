package com.gotasoft.mosojkausay.model.entities.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SlideResponse(var id: Int = 0, var enlace: String = "")