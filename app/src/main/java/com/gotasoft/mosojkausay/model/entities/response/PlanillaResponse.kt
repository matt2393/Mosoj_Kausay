package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class PlanillaResponse(
    var activo: Boolean = false,
    var ci: String = "",
    var createdAt: String = "",
    var deletedAt: String? = null,
    var fin: String = "",
    var inicio: String = "",
    var planilla_id: String = "",
    var tipo: String = "",
    var updatedAt: String? = ""
) : Parcelable