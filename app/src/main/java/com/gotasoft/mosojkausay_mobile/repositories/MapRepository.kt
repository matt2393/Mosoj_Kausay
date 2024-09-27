package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.MODO_CAMINATA
import com.gotasoft.mosojkausay_mobile.model.entities.response.RutasMap
import com.gotasoft.mosojkausay_mobile.model.network.RutasMaps.RetrofitRutas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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