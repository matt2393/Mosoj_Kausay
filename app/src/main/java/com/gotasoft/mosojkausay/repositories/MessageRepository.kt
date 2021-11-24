package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.request.MessageRequest
import com.gotasoft.mosojkausay.model.entities.response.MessageResponse
import com.gotasoft.mosojkausay.model.network.ApiRest
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