package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.response.PersonalResponse
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PersonalRepository {
    suspend fun getPersonal(token: String): Flow<ArrayList<PersonalResponse>> =
        flow {
            val res = ApiRest.request.getPersonal(token = token)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)
}