package com.gotasoft.mosojkausay.model.entities.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class NoticiaShowRes(
    @Json(name = "multimediaList")
    var multimediaList: List<Multimedia>? = listOf(),
    @Json(name = "noticia")
    var noticia: Noticia? = Noticia()
) : Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Multimedia(
        @Json(name = "createdAt")
        var createdAt: String? = "",
        @Json(name = "deletedAt")
        var deletedAt: String? = null,
        @Json(name = "enlace")
        var enlace: String? = "",
        @Json(name = "id")
        var id: Int? = 0,
        @Json(name = "multimedia_tipo")
        var multimediaTipo: String? = "",
        @Json(name = "publicacion_id")
        var publicacionId: Int? = 0,
        @Json(name = "publicacion_tipo")
        var publicacionTipo: String? = "",
        @Json(name = "titulo")
        var titulo: String? = "",
        @Json(name = "updatedAt")
        var updatedAt: String? = ""
    ) : Parcelable

    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Noticia(
        @Json(name = "ci")
        var ci: String? = "",
        @Json(name = "contenido")
        var contenido: String? = "",
        @Json(name = "createdAt")
        var createdAt: String? = "",
        @Json(name = "deletedAt")
        var deletedAt: String? = null,
        @Json(name = "id")
        var id: Int? = 0,
        @Json(name = "publicado")
        var publicado: Boolean? = false,
        @Json(name = "titulo")
        var titulo: String? = "",
        @Json(name = "updatedAt")
        var updatedAt: String? = ""
    ) : Parcelable
}