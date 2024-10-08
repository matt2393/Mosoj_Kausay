package com.gotasoft.mosojkausay_mobile.view.seguimiento.edit_seg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.request.SeguimientoEditRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.MessGenericResponse
import com.gotasoft.mosojkausay_mobile.repositories.SeguimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EditSegViewModel(private val seguimientoRepository: SeguimientoRepository = SeguimientoRepository()): ViewModel() {
    private val _segs: MutableStateFlow<StateData<MessGenericResponse>> = MutableStateFlow(
        StateData.None)
    val segs: StateFlow<StateData<MessGenericResponse>> get() = _segs

    fun editSeg(token: String,
                id: Int,
                seguimientoEditRequest: SeguimientoEditRequest
    ) {
        _segs.value = StateData.Loading
        viewModelScope.launch {
            seguimientoRepository.editSeg(token, id, seguimientoEditRequest)
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
}