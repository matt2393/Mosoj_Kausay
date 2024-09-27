package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.model.entities.request.MessageRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.MessageResponse
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MessageRepository {

    suspend fun addMess(token: String, mess: MessageRequest): Flow<MessageResponse> =
        flow {
            val res = ApiRest.request.addMess(token, mess)
            emit(res)
        }.flowOn(Dispatchers.IO)
}