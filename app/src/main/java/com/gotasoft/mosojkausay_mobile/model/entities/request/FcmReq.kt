package com.gotasoft.mosojkausay_mobile.model.entities.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FcmReq(
    val fsm_token: String
)
