package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.model.entities.response.FotoMMAddResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.FotoMMResponse
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FotoMMRepository {

    suspend fun getFotosMM(token: String = "",
                           id: Int): Flow<ArrayList<FotoMMResponse>> =
        flow {
            val res = ApiRest.request.getFotosMM(token, id)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun addFotoMM(token: String = "",
                          desc: String = "",
                          mmId: Int = 0,
                          foto: File): Flow<FotoMMAddResponse> =
        flow {
            val req = MultipartBody.Part.createFormData("foto", foto.name,
                foto.asRequestBody("multipart/form-data".toMediaTypeOrNull()))
            val res = ApiRest.request.addFotoMM(token, desc, mmId, req)
            emit(res)
        }.flowOn(Dispatchers.IO)
}