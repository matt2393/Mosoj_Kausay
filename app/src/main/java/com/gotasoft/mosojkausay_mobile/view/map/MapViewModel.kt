package com.estrelladelsur.apptecnico.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.MODO_CAMINATA
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.response.RutasMap
import com.gotasoft.mosojkausay_mobile.repositories.MapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MapViewModel(private val mapRepository: MapRepository = MapRepository()): ViewModel() {

    private val _rutas: MutableStateFlow<StateData<RutasMap>> = MutableStateFlow(StateData.None)
    val rutas: StateFlow<StateData<RutasMap>> get() = _rutas

    fun getRutas(origen: String,
                 des: String,
                 key: String,
                 alternativas: Boolean = true,
                 modo: String = MODO_CAMINATA) {
        viewModelScope.launch {
            _rutas.value = StateData.Loading
            mapRepository.getRutas(origen, des, key, alternativas, modo)
                .catch {
                    _rutas.value = StateData.None
                    _rutas.value = StateData.Error(it)
                }
                .collect {
                    _rutas.value = StateData.None
                    _rutas.value = StateData.Success(it)
                }
        }
    }

}