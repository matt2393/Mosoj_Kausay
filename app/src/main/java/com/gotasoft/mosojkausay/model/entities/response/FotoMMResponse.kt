package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class FotoMMResponse(
    var descripcion: String = "",
    var foto: String = "",
    var id: Int = 0,
    var momento_magico_id: Int = 0
) : Parcelable