package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.response.VillaResponse
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class VillaRepository {

    suspend fun getVillas(): Flow<ArrayList<VillaResponse>>
        = flow {
            val res = ApiRest.request.getVillas()
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)
}