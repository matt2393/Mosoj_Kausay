package com.gotasoft.mosojkausay.model.entities.request


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class TestimonioAdRequest(
    var momento_magico_id: Int = 0,
    var pregunta: String = "",
    var testimonio: String = ""
) : Parcelable