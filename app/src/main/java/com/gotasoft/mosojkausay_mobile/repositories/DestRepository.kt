package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.model.entities.response.DestinatarioResponse
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DestRepository {

    suspend fun getDest(token: String, messId: Int): Flow<ArrayList<DestinatarioResponse>> =
        flow {
            val res = ApiRest.request.getDestinatarios(token, messId)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)
}