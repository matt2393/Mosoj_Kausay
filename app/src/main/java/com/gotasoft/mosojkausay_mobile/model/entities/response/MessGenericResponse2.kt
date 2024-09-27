package com.gotasoft.mosojkausay_mobile.model.entities.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessGenericResponse2(var success: String = "",
                               var message: String = "")