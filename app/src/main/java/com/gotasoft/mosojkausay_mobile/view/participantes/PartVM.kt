package com.gotasoft.mosojkausay_mobile.view.participantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.response.PartTotales
import com.gotasoft.mosojkausay_mobile.repositories.ParticipanteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PartVM(private val participanteRepository: ParticipanteRepository = ParticipanteRepository()): ViewModel() {

    private val _totales: MutableStateFlow<StateData<List<PartTotales>>> = MutableStateFlow(StateData.None)
    val totales: StateFlow<StateData<List<PartTotales>>> get() = _totales

    fun getTotales(gestion: String) {
        viewModelScope.launch {
            participanteRepository.getTotales(gestion)
                .catch {
                    _totales.value = StateData.None
                    _totales.value = StateData.Error(it)
                }
                .collect {
                    _totales.value = StateData.None
                    _totales.value = StateData.Success(it)
                }
        }
    }
}