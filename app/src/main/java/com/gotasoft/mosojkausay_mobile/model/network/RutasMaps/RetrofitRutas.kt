package com.gotasoft.mosojkausay_mobile.model.network.RutasMaps

import com.gotasoft.mosojkausay_mobile.URL_RUTAS_MAPS
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitRutas {
    val request: RetrofitRutasRequest = Retrofit.Builder()
        .baseUrl(URL_RUTAS_MAPS)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build())
        .build()
        .create(RetrofitRutasRequest::class.java)

}