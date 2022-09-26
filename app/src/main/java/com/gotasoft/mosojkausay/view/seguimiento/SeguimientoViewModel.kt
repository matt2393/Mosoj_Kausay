package com.gotasoft.mosojkausay.view.seguimiento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.request.SegActivoInactivoRequest
import com.gotasoft.mosojkausay.model.entities.response.MessGenericResponse
import com.gotasoft.mosojkausay.model.entities.response.MessGenericResponse2
import com.gotasoft.mosojkausay.model.entities.response.SeguimientoResponse
import com.gotasoft.mosojkausay.repositories.SeguimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SeguimientoViewModel(private val seguimientoRepository: SeguimientoRepository = SeguimientoRepository()): ViewModel() {
    private val _segs: MutableStateFlow<StateData<ArrayList<SeguimientoResponse>>> = MutableStateFlow(StateData.None)
    val segs: StateFlow<StateData<ArrayList<SeguimientoResponse>>> get() = _segs

    fun getSegs(token: String = "",
                gestion: String = "",
                tipo: String = "",
                activo: Int = 1) {
        _segs.value = StateData.Loading
        viewModelScope.launch {
            seguimientoRepository.getSegs(token, gestion, tipo, activo)
                .catch {
                    _segs.value = StateData.None
                    _segs.value = StateData.Error(it)
                }
                .collect {
                    _segs.value = StateData.None
                    _segs.value = StateData.Success(it)
                }
        }
    }

    private val _activeSeg: MutableStateFlow<StateData<MessGenericResponse2>> = MutableStateFlow(StateData.None)
    val activeSeg: StateFlow<StateData<MessGenericResponse2>> get() = _activeSeg

    private val _inactiveSeg: MutableStateFlow<StateData<MessGenericResponse2>> = MutableStateFlow(StateData.None)
    val inactiveSeg: StateFlow<StateData<MessGenericResponse2>> get() = _inactiveSeg

    fun activeSegimiento(token: String, id: Int) {
        _activeSeg.value = StateData.Loading
        viewModelScope.launch {
            seguimientoRepository.editSegsActivoInactivo(
                token, id, SegActivoInactivoRequest(1)
            ).catch {
                _activeSeg.value = StateData.None
                _activeSeg.value = StateData.Error(it)
            }.collect {
                _activeSeg.value = StateData.None
                _activeSeg.value = StateData.Success(it)
            }
        }
    }

    fun inactiveSegimiento(token: String, id: Int) {
        _inactiveSeg.value = StateData.Loading
        viewModelScope.launch {
            seguimientoRepository.editSegsActivoInactivo(
                token, id, SegActivoInactivoRequest(0)
            ).catch {
                _activeSeg.value = StateData.None
                _inactiveSeg.value = StateData.Error(it)
            }.collect {
                _activeSeg.value = StateData.None
                _inactiveSeg.value = StateData.Success(it)
            }
        }
    }
}