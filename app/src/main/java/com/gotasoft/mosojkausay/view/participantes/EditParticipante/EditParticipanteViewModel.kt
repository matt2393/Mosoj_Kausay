package com.gotasoft.mosojkausay.view.participantes.EditParticipante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.request.ParticipanteRequest
import com.gotasoft.mosojkausay.model.entities.response.ResponseGeneric
import com.gotasoft.mosojkausay.repositories.ParticipanteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EditParticipanteViewModel(private val participanteRepository: ParticipanteRepository = ParticipanteRepository()): ViewModel() {
    private val _savePart: MutableStateFlow<StateData<ResponseGeneric>> = MutableStateFlow(StateData.None)
    val savePart: StateFlow<StateData<ResponseGeneric>> get() = _savePart


    fun editParticipante(participanteRequest: ParticipanteRequest) {
        viewModelScope.launch {
            _savePart.value = StateData.Loading
            participanteRepository.editParticipante(participanteRequest)
                .catch { _savePart.value = StateData.Error(it) }
                .collect { _savePart.value = StateData.Success(it) }
        }
    }
}