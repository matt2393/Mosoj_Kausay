package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.request.ParticipanteRequest
import com.gotasoft.mosojkausay.model.entities.response.AllParticipanteResponse
import com.gotasoft.mosojkausay.model.entities.response.ParticipanteResponse
import com.gotasoft.mosojkausay.model.entities.response.ResponseGeneric
import com.gotasoft.mosojkausay.model.entities.response.SuccessRes
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ParticipanteRepository {

    suspend fun getParticipantes(key: String = "nombre_completo",
                         value: String = "",
                         order: String = "asc",
                         by: String = "child_number",
                         items: Int = 10,
                         village: String = "",
                         page: Int = 1,
                         from: Int = 0,
                         to: Int = 5): Flow<AllParticipanteResponse> =
        flow {
            val res = ApiRest.request.getParticipantes(key, value, order, by, items, village, page, from, to)
            emit(res)
        }.flowOn(Dispatchers.IO)

    suspend fun editParticipante(participanteRequest: ParticipanteRequest): Flow<ResponseGeneric> =
        flow {
            val res = ApiRest.request.editParticipante(participanteRequest)
            emit(res)
        }.flowOn(Dispatchers.IO)

    suspend fun savePhoto(childNumber: String, type: String, photo: File): Flow<SuccessRes> = flow<SuccessRes> {
        val req = MultipartBody.Part.createFormData("foto", photo.name,
            photo.asRequestBody("multipart/form-data".toMediaTypeOrNull()))
        val res = ApiRest.request.savePhotoPart(childNumber, type, req)
        emit(res)
    }.flowOn(Dispatchers.IO)

}