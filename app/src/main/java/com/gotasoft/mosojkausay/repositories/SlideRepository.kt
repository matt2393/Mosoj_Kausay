package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.response.SlideResponse
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SlideRepository {

    suspend fun getSlides(): Flow<ArrayList<SlideResponse>> =
        flow {
            val res = ApiRest.request.getSlides()
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)
}