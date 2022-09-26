package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.request.SegActivoInactivoRequest
import com.gotasoft.mosojkausay.model.entities.request.SeguimientoCreateRequest
import com.gotasoft.mosojkausay.model.entities.request.SeguimientoEditRequest
import com.gotasoft.mosojkausay.model.entities.response.MessGenericResponse
import com.gotasoft.mosojkausay.model.entities.response.MessGenericResponse2
import com.gotasoft.mosojkausay.model.entities.response.SeguimientoResponse
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Header
import retrofit2.http.Query

class SeguimientoRepository {

    suspend fun getSegs(token: String = "",
                        gestion: String = "",
                        tipo: String = "",
                        activo: Int = 1): Flow<ArrayList<SeguimientoResponse>> =
        flow {
            val res = ApiRest.request.getSegs(token, gestion, tipo, activo)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun getMisSegs(token: String = "",
                           tipo: String = ""): Flow<ArrayList<SeguimientoResponse>> =
        flow {
            val res = ApiRest.request.getMisSegs(token, tipo)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun addSeg(token: String,
                       seguimientoCreateRequest: SeguimientoCreateRequest): Flow<SeguimientoResponse> =
        flow {
            val res = ApiRest.request.addSegs(token, seguimientoCreateRequest)
            emit(res)
        }.flowOn(Dispatchers.IO)

    suspend fun editSeg(token: String,
                        id: Int,
                        seguimientoEditRequest: SeguimientoEditRequest): Flow<MessGenericResponse> =
        flow {
            val res = ApiRest.request.editSegs(token, id, seguimientoEditRequest)
            emit(res)
        }.flowOn(Dispatchers.IO)

    suspend fun editSegsActivoInactivo(token: String,
                                       id: Int,
                                       segActivoInactivoRequest: SegActivoInactivoRequest): Flow<MessGenericResponse2> =
        flow {
            val res = ApiRest.request.editSegsActivoInactivo(token, id, segActivoInactivoRequest)
            emit(res)
        }.flowOn(Dispatchers.IO)
}