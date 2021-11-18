package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class AllParticipanteResponse(
    var subtotal: Int = 0,
    var total: Int = 0,
    var participantes: List<ParticipanteResponse> = arrayListOf()
) : Parcelable