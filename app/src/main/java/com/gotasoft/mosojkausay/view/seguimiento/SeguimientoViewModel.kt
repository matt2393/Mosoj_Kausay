package com.gotasoft.mosojkausay.view.seguimiento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
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
}