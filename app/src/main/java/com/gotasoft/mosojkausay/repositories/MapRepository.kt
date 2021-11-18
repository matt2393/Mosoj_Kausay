package com.gotasoft.mosojkausay.repositories

import com.gotasoft.mosojkausay.MODO_CAMINATA
import com.gotasoft.mosojkausay.ResultData
import com.gotasoft.mosojkausay.model.entities.response.RutasMap
import com.gotasoft.mosojkausay.model.network.RutasMaps.RetrofitRutas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class MapRepository {

    suspend fun getRutas(origen: String,
                         des: String,
                         key: String,
                         alternativas: Boolean = true,
                         modo: String = MODO_CAMINATA): Flow<RutasMap> =
        flow {
            val res = RetrofitRutas.request.getRuta(origen, des, key, alternativas, modo)
            emit(res)
        }.flowOn(Dispatchers.IO)
}