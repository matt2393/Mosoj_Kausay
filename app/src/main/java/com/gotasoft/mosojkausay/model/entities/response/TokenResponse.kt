package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@Parcelize
@JsonClass(generateAdapter = true)
data class TokenResponse(
    var iat: Int = 0,
    var rol: String = "",
    var username: String = ""
) : Parcelable