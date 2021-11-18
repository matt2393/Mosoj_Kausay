package com.gotasoft.mosojkausay.utils

import android.util.Base64
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