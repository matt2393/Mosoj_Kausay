package com.gotasoft.mosojkausay.view.mess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.response.MensajeResponse
import com.gotasoft.mosojkausay.repositories.MensajeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MessViewModel(private val mensajeRepository: MensajeRepository = MensajeRepository()): ViewModel() {
    private val _mess: MutableStateFlow<StateData<ArrayList<MensajeResponse>>> = MutableStateFlow(StateData.None)
    val mess: StateFlow<StateData<ArrayList<MensajeResponse>>> get() = _mess

    fun getMess(token: String = "",
                value: String = "",
                items: Int = 10,
                page: Int = 1) {
        _mess.value = StateData.Loading
        viewModelScope.launch {
            mensajeRepository.getMensajes(token, value, items, page)
                .catch { _mess.value = StateData.Error(it) }
                .collect { _mess.value = StateData.Success(it) }
        }
    }
}