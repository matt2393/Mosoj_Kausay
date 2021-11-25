package com.gotasoft.mosojkausay.view.mess.destinatarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.response.DestinatarioResponse
import com.gotasoft.mosojkausay.repositories.DestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DestViewModel(private val destRepository: DestRepository = DestRepository()): ViewModel() {
    private val _dests: MutableStateFlow<StateData<ArrayList<DestinatarioResponse>>> = MutableStateFlow(StateData.None)
    val dests: StateFlow<StateData<ArrayList<DestinatarioResponse>>> get() = _dests

    fun getDest(token: String, messId: Int) {
        _dests.value = StateData.Loading
        viewModelScope.launch {
            destRepository.getDest(token, messId)
                .catch {
                    _dests.value = StateData.None
                    _dests.value = StateData.Error(it)
                }
                .collect {
                    _dests.value = StateData.None
                    _dests.value = StateData.Success(it)
                }
        }
    }
}