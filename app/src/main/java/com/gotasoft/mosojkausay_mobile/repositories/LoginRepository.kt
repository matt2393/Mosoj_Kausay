package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.model.entities.request.FcmReq
import com.gotasoft.mosojkausay_mobile.model.entities.request.LoginRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.LoginResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.MessGenericResponse
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<LoginResponse> {
        return flow {
            val res = ApiRest.request.login(loginRequest)
            emit(res)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun editFCMToken(token: String, fcmReq: FcmReq): Flow<MessGenericResponse> = flow {
        val res = ApiRest.request.editFCMToken(token, fcmReq)
        emit(res)
    }.flowOn(Dispatchers.IO)
}