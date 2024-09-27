package com.gotasoft.mosojkausay_mobile.view.mess.crear_mess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.request.MessageRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.MessageResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.PersonalResponse
import com.gotasoft.mosojkausay_mobile.repositories.MessageRepository
import com.gotasoft.mosojkausay_mobile.repositories.PersonalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CrearMessViewModel(private val messageRepository: MessageRepository = MessageRepository(),
                         private val personalRepository: PersonalRepository = PersonalRepository()
): ViewModel() {

    private val _personal: MutableStateFlow<StateData<ArrayList<PersonalResponse>>> = MutableStateFlow(StateData.None)
    val personal: StateFlow<StateData<ArrayList<PersonalResponse>>> get() = _personal

    fun getPersonal(token: String) {
        _personal.value = StateData.Loading
        viewModelScope.launch {
            personalRepository.getPersonal(token)
                .catch { _personal.value = StateData.Error(it) }
                .collect { _personal.value = StateData.Success(it) }
        }
    }

    private val _mess: MutableStateFlow<StateData<MessageResponse>> = MutableStateFlow(StateData.None)
    val mess: StateFlow<StateData<MessageResponse>> get() = _mess

    fun addMess(token: String, messageRequest: MessageRequest) {
        _mess.value = StateData.Loading
        viewModelScope.launch {
            messageRepository.addMess(token, messageRequest)
                .catch { _mess.value = StateData.Error(it) }
                .collect { _mess.value = StateData.Success(it) }
        }
    }

}