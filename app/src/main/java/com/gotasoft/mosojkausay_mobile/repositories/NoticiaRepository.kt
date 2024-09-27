package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.model.entities.response.NoticiaPublicadaResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.NoticiaShowRes
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NoticiaRepository {

    suspend fun getNoticias(value: String = "",
                            items: Int = 10,
                            page: Int = 1): Flow<ArrayList<NoticiaPublicadaResponse>> =
        flow {
            val res = ApiRest.request.getNoticias(value, items, page)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun showNoticia(id: Int): Flow<NoticiaShowRes> = flow {
        val res = ApiRest.request.showNoticia(id)
        emit(res)
    }.flowOn(Dispatchers.IO)
}