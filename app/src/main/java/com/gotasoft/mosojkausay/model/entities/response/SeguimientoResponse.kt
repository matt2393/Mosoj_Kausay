package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.gotasoft.mosojkausay.model.entities.request.SeguimientoEditRequest
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class SeguimientoResponse(
    var activo: Boolean = false,
    var ejecutado: Int = 0,
    var gestion: Int = 0,
    var id: Int = 0,
    var mes: Int = 0,
    var modelo_programatico: String = "",
    var programado: Int = 0,
    var tipo: String = ""
) : Parcelable {
    fun toSegEditReq(): SeguimientoEditRequest {
        val seguimientoEditRequest = SeguimientoEditRequest()
        seguimientoEditRequest.activo = activo
        seguimientoEditRequest.ejecutado = ejecutado
        seguimientoEditRequest.gestion = gestion
        seguimientoEditRequest.mes = mes
        seguimientoEditRequest.modelo_programatico = modelo_programatico
        seguimientoEditRequest.programado = programado
        seguimientoEditRequest.tipo = tipo
        return seguimientoEditRequest
    }
}