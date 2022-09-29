package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.ResultData
import com.gotasoft.mosojkausay.model.entities.request.FcmReq
import com.gotasoft.mosojkausay.model.entities.request.LoginRequest
import com.gotasoft.mosojkausay.model.entities.response.LoginResponse
import com.gotasoft.mosojkausay.model.entities.response.MessGenericResponse
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

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