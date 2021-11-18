package com.gotasoft.mosojkausay.model.network.RutasMaps

import com.gotasoft.mosojkausay.MODO_CAMINATA
import com.gotasoft.mosojkausay.model.entities.response.RutasMap
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitRutasRequest {
    @GET("directions/json")
    suspend fun getRuta(@Query("origin") origen: String,
                        @Query("destination") des: String,
                        @Query("key") key: String,
                        @Query("alternatives") alternativas: Boolean = true,
                        @Query("mode") modo: String = MODO_CAMINATA): RutasMap
}