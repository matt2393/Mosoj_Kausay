package com.gotasoft.mosojkausay.view.participantes.list_participantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.response.AllParticipanteResponse
import com.gotasoft.mosojkausay.model.entities.response.ParticipanteResponse
import com.gotasoft.mosojkausay.repositories.ParticipanteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListParticipantesViewModel(private val participanteRepository: ParticipanteRepository = ParticipanteRepository()): ViewModel() {
    private val _listParticipantesState = MutableStateFlow<StateData<AllParticipanteResponse>>(StateData.None)
    val listParticipantesState: StateFlow<StateData<AllParticipanteResponse>> get() =  _listParticipantesState

    fun getParticipantes(key: String = "nombre_completo",
                         value: String = "",
                         order: String = "asc",
                         by: String = "child_number",
                         items: Int = 10,
                         village: String = "",
                         page: Int = 1,
                         from: Int = 0,
                         to: Int = 5) {
        viewModelScope.launch {
            _listParticipantesState.value = StateData.Loading
            participanteRepository.getParticipantes(key, value, order, by, items, village, page, from, to)
                .catch { _listParticipantesState.value = StateData.Error(it) }
                .collect { _listParticipantesState.value = StateData.Success(it) }
        }
    }

}