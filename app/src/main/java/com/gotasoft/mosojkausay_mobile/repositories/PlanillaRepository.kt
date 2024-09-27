package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.model.entities.response.PlanillaResponse
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlanillaRepository {

    suspend fun getPlanillas(token: String, activo: Int, tipo: String): Flow<ArrayList<PlanillaResponse>> =
        flow {
            val res = ApiRest.request.getPlanillas(token, activo, tipo)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)
}