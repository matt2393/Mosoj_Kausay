package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.model.entities.request.ParticipanteRequest
import com.gotasoft.mosojkausay.model.entities.response.AllParticipanteResponse
import com.gotasoft.mosojkausay.model.entities.response.ParticipanteResponse
import com.gotasoft.mosojkausay.model.entities.response.ResponseGeneric
import com.gotasoft.mosojkausay.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


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


}