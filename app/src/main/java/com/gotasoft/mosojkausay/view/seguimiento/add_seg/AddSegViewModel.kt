package com.gotasoft.mosojkausay.view.seguimiento.add_seg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.request.SeguimientoCreateRequest
import com.gotasoft.mosojkausay.model.entities.response.SeguimientoResponse
import com.gotasoft.mosojkausay.repositories.SeguimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddSegViewModel(private val seguimientoRepository: SeguimientoRepository = SeguimientoRepository()): ViewModel() {
    private val _segs: MutableStateFlow<StateData<SeguimientoResponse>> = MutableStateFlow(
        StateData.None)
    val segs: StateFlow<StateData<SeguimientoResponse>> get() = _segs

    fun addSeg(token: String,
               seguimientoCreateRequest: SeguimientoCreateRequest) {
        _segs.value = StateData.Loading
        viewModelScope.launch {
            seguimientoRepository.addSeg(token, seguimientoCreateRequest)
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