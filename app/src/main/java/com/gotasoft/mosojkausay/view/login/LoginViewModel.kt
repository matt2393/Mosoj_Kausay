package com.gotasoft.mosojkausay.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.request.LoginRequest
import com.gotasoft.mosojkausay.model.entities.response.LoginResponse
import com.gotasoft.mosojkausay.repositories.LoginRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository = LoginRepository()): ViewModel() {

    private val _loginState = MutableStateFlow<StateData<LoginResponse>>(StateData.None)
    val loginState: StateFlow<StateData<LoginResponse>> get() = _loginState

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _loginState.value = StateData.Loading
            loginRepository.login(loginRequest)
                .catch { _loginState.value = StateData.Error(it) }
                .collect { _loginState.value = StateData.Success(it) }
        }
    }

}