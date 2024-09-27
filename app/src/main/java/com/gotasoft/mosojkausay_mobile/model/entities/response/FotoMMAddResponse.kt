package com.gotasoft.mosojkausay_mobile.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class FotoMMAddResponse(
    var message: String = "",
    var success: Boolean = false
) : Parcelable