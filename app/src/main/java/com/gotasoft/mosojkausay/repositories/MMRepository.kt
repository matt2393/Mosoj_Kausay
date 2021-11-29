package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.request.MMRequest
import com.gotasoft.mosojkausay.model.entities.response.MMResponse
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MMRepository {

    suspend fun getMM(token: String = "",
                      value: String = "",
                      items: Int = 10,
                      page: Int = 1) : Flow<ArrayList<MMResponse>> =
        flow {
            val res = ApiRest.request.getMM(token, value, items, page)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun addMM(token: String = "", mmRequest: MMRequest): Flow<MMResponse> =
        flow {
            val res = ApiRest.request.addMM(token, mmRequest)
            emit(res)
        }.flowOn(Dispatchers.IO)
}