package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.request.ValidarCorresRequest
import com.gotasoft.mosojkausay.model.entities.response.*
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CorresRepository {

    /**
     * Reply
     */
    suspend fun getCorresReply(token: String, value: String, planilla_id: String, validacion: String): Flow<ArrayList<ReplyResponse>> =
        flow {
            val res = ApiRest.request.getCorresReply(token = token,
                value = value, planilla_id = planilla_id,validacion = validacion)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun getMisCorresReply(token: String, value: String, planilla_id: String, validacion: String): Flow<ArrayList<ReplyResponse>> =
        flow {
            val res = ApiRest.request.getMisCorresReply(token = token,
                value = value, planilla_id = planilla_id,validacion = validacion)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    /**
     * Welcome
     */
    suspend fun getCorresWelcome(token: String, value: String, planilla_id: String, validacion: String): Flow<ArrayList<WelcomeResponse>> =
        flow {
            val res = ApiRest.request.getCorresWelcome(token = token,
                value = value, planilla_id = planilla_id,validacion = validacion)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun getMisCorresWelcome(token: String, value: String, planilla_id: String, validacion: String): Flow<ArrayList<WelcomeResponse>> =
        flow {
            val res = ApiRest.request.getMisCorresWelcome(token = token,
                value = value, planilla_id = planilla_id,validacion = validacion)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    /**
     * Dfc
     */
    suspend fun getCorresDfc(token: String, value: String, planilla_id: String, validacion: String): Flow<ArrayList<DfcResponse>> =
        flow {
            val res = ApiRest.request.getCorresDfc(token = token,
                value = value, planilla_id = planilla_id,validacion = validacion)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun getMisCorresDfc(token: String, value: String, planilla_id: String, validacion: String): Flow<ArrayList<DfcResponse>> =
        flow {
            val res = ApiRest.request.getMisCorresDfc(token = token,
                value = value, planilla_id = planilla_id,validacion = validacion)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    /**
     * Unavailable
     */
    suspend fun getCorresUnavailable(token: String, value: String, planilla_id: String, validacion: String): Flow<ArrayList<UnavailableResponse>> =
        flow {
            val res = ApiRest.request.getCorresUnavailable(token = token,
                value = value, planilla_id = planilla_id,validacion = validacion)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun getMisCorresUnavailable(token: String, value: String, planilla_id: String, validacion: String): Flow<ArrayList<UnavailableResponse>> =
        flow {
            val res = ApiRest.request.getMisCorresUnavailable(token = token,
                value = value, planilla_id = planilla_id,validacion = validacion)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)


    /**
     * validar
     */

    suspend fun validarCorres(token: String, id: String, validarCorresRequest: ValidarCorresRequest): Flow<ValidarCorresResponse> =
        flow {
            val res = ApiRest.request.validarCorres(token, id, validarCorresRequest)
            emit(res)
        }.flowOn(Dispatchers.IO)
}