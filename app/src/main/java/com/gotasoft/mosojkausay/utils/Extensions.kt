package com.gotasoft.mosojkausay.utils

import android.util.Base64
import com.gotasoft.mosojkausay.US_ADMIN
import com.gotasoft.mosojkausay.US_FACILITADOR
import com.gotasoft.mosojkausay.US_PATROCINIO
import com.gotasoft.mosojkausay.US_TECNICO
import com.gotasoft.mosojkausay.model.entities.response.TokenResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.nio.charset.Charset

fun String.decodeJWT(): ArrayList<String>{
    val parts = split(".")
    val res = arrayListOf<String>()
    parts.forEach {
        res.add(it.base64Decode())
    }
    return res
}
fun String.base64Decode(): String {
    var decode = ""
    try {
        decode = String(
            Base64.decode(this, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING),
            Charset.defaultCharset()
        )
    } catch (ex: IllegalArgumentException) {
        throw RuntimeException("Error")
    }
    return decode
}

fun String.fromJsonToken(): TokenResponse {
    val moshi = Moshi.Builder().build()
    val adapter: JsonAdapter<TokenResponse> = moshi.adapter(TokenResponse::class.java)
    return adapter.fromJson(this) ?: TokenResponse()
}


fun String.tokenTipoUsApi(): String {
    val dataToken = this.decodeJWT()
    return if (dataToken.size > 1) {
        val tokenR = dataToken[1].fromJsonToken()
        tokenR.rol.uppercase()
    } else "PARTICIPANTE"

}
fun String.tokenTipoUs(): TipoPersonal {
    val dataToken = this.decodeJWT()
    return if (dataToken.size > 1) {
        val tokenR = dataToken[1].fromJsonToken()
        when (tokenR.rol) {
            US_ADMIN -> TipoPersonal.ADMIN
            US_PATROCINIO -> TipoPersonal.PATROCINIO
            US_FACILITADOR -> TipoPersonal.FACILITADOR
            US_TECNICO -> TipoPersonal.TECNICO
            else -> TipoPersonal.PARTICIPANTE
        }
    } else TipoPersonal.PARTICIPANTE

}