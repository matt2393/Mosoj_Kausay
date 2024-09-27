package com.gotasoft.mosojkausay_mobile.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@Parcelize
@JsonClass(generateAdapter = true)
data class ContadorRes(
    var cantidad: Int = 0,
    var message: String = "",
    var success: Boolean = false
) : Parcelable