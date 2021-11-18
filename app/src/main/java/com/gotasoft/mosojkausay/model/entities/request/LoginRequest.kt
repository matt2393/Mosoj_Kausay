package com.gotasoft.mosojkausay.model.entities.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(var username: String = "",
                        var password: String = "")
