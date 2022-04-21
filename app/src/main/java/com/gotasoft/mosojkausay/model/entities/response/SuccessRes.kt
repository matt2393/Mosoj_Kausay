package com.gotasoft.mosojkausay.model.entities.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class SuccessRes(
    @Json(name = "message")
    var message: String? = "",
    @Json(name = "success")
    var success: Boolean? = false
) : Parcelable