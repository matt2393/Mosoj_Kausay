package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.response.MensajeResponse
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MensajeRepository {

    suspend fun getMensajes(token: String = "",
                            value: String = "",
                            items: Int = 10,
                            page: Int = 1): Flow<ArrayList<MensajeResponse>> =
        flow {
            val res = ApiRest.request.getMensajes(token, value, items, page)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)
}