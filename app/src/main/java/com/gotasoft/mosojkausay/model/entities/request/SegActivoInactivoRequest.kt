package com.gotasoft.mosojkausay.model.entities.request


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SegActivoInactivoRequest(
    var activo: Int = 0
) : Parcelable