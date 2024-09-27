package com.gotasoft.mosojkausay_mobile.model.entities.request


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@Parcelize
@JsonClass(generateAdapter = true)
data class SegActivoInactivoRequest(
    var activo: Int = 0
) : Parcelable