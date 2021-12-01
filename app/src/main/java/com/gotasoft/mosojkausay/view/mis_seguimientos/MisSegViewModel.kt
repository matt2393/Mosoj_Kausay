package com.gotasoft.mosojkausay.view.mis_seguimientos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.request.SegActivoInactivoRequest
import com.gotasoft.mosojkausay.model.entities.request.SeguimientoEditRequest
import com.gotasoft.mosojkausay.model.entities.response.MessGenericResponse
import com.gotasoft.mosojkausay.model.entities.response.SeguimientoResponse
import com.gotasoft.mosojkausay.repositories.SeguimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MisSegViewModel(private val seguimientoRepository: SeguimientoRepository = SeguimientoRepository()): ViewModel() {

    private val _segs: MutableStateFlow<StateData<ArrayList<SeguimientoResponse>>> = MutableStateFlow(
        StateData.None)
    val segs: StateFlow<StateData<ArrayList<SeguimientoResponse>>> get() = _segs

    fun getMisSegs(token: String = "",
                   tipo: String = "") {
        _segs.value = StateData.Loading
        viewModelScope.launch {
            seguimientoRepository.getMisSegs(token, tipo)
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

    private val _editSegs: MutableStateFlow<StateData<MessGenericResponse>> = MutableStateFlow(
        StateData.None)
    val editSegs: StateFlow<StateData<MessGenericResponse>> get() = _editSegs

    fun getEditSegs(token: String = "",
                    id: Int = 0,
                    seguimientoEditRequest: SeguimientoEditRequest) {
        _editSegs.value = StateData.Loading
        viewModelScope.launch {
            seguimientoRepository.editSeg(token, id, seguimientoEditRequest)
                .catch {
                    _editSegs.value = StateData.None
                    _editSegs.value = StateData.Error(it)
                }
                .collect {
                    _editSegs.value = StateData.None
                    _editSegs.value = StateData.Success(it)
                }
        }
    }
}