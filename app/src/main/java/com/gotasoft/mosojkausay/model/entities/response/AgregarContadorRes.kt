package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@Parcelize
@JsonClass(generateAdapter = true)
data class AgregarContadorRes(
    var cantidad: Int = 0,
    var fecha: String = "",
    var message: String = "",
    var success: Boolean = false
) : Parcelable