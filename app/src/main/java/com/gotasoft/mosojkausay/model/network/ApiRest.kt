package com.gotasoft.mosojkausay.model.network

import com.gotasoft.mosojkausay.URL_SERVER
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiRest {
    val request: ApiRequest = Retrofit.Builder()
        .baseUrl(URL_SERVER)
        .client(OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(ApiRequest::class.java)
}