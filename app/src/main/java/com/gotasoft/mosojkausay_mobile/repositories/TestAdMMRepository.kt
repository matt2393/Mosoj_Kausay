package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.model.entities.request.TestimonioAdRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.TestimonioAdResponse
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class TestAdMMRepository {

    suspend fun getTestAd(token: String = "",
                          id: Int): Flow<ArrayList<TestimonioAdResponse>> =
        flow {
            val res = ApiRest.request.getTestAdMM(token, id)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun addTestAd(token: String, testimonioAdRequest: TestimonioAdRequest): Flow<TestimonioAdResponse> =
        flow {
            val res = ApiRest.request.addTestAdMM(token, testimonioAdRequest)
            emit(res)
        }.flowOn(Dispatchers.IO)
}