package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@Parcelize
@JsonClass(generateAdapter = true)
data class LoginResponse(
    var message: String = "",
    var success: Boolean = false,
    var token: String = ""
) : Parcelable