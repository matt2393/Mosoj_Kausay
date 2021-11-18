package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.response.NoticiaPublicadaResponse
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Header
import retrofit2.http.Query

class NoticiaRepository {

    suspend fun getNoticias(value: String = "",
                            items: Int = 10,
                            page: Int = 1): Flow<ArrayList<NoticiaPublicadaResponse>> =
        flow {
            val res = ApiRest.request.getNoticias(value, items, page)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)
}