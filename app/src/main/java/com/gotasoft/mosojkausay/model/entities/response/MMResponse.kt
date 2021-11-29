package com.gotasoft.mosojkausay.model.entities.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Parcelize
data class MMResponse(
    @Json(name = "Fotos")var fotos: List<Foto> = listOf(),
    @Json(name = "Participante")var participante: Participante = Participante(),
    @Json(name = "Personal")var personal: Personal = Personal(),
    var TestimonioAdicionals: List<TestimonioAdicional> = listOf(),
    var child_number: String = "",
    var ci: String = "",
    var consentimiento: Boolean = false,
    var createdAt: String = "",
    var deletedAt: String? = null,
    var edad: Int = 0,
    var fecha_envio: String = "",
    var id: Int = 0,
    var modelo_programatico: String = "",
    var nombre_completo: String = "",
    var ocupacion: String = "",
    var organizacion_socia: String = "",
    var referencia_familiar: String = "",
    var relacion_childfund: String = "",
    var resultado_intermedio: String = "",
    var testimonio_1: String? = "",
    var testimonio_2: String? = null,
    var testimonio_3: String? = null,
    var testimonio_4: String? = null,
    var tipo_momento_magico: String = "",
    var updatedAt: String? = ""
) : Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Foto(
        var descripcion: String = "",
        var foto: String = "",
        var id: Int = 0,
        var momento_magico_id: Int = 0
    ) : Parcelable
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Participante(
        var comunidad: String = "",
        var id_village: Int = 0,
        var nombre_completo: String = ""
    ) : Parcelable
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Personal(
        var nombre_completo: String = ""
    ) : Parcelable
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class TestimonioAdicional(
        var id: Int = 0,
        var momento_magico_id: Int = 0,
        var pregunta: String = "",
        var testimonio: String = ""
    ) : Parcelable
}