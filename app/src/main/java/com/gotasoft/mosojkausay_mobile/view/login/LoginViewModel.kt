package com.gotasoft.mosojkausay_mobile.view.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.request.FcmReq
import com.gotasoft.mosojkausay_mobile.model.entities.request.LoginRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.AgregarContadorRes
import com.gotasoft.mosojkausay_mobile.model.entities.response.LoginResponse
import com.gotasoft.mosojkausay_mobile.repositories.ContadorRepository
import com.gotasoft.mosojkausay_mobile.repositories.LoginRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository = LoginRepository(),
    private var contadorRepository: ContadorRepository = ContadorRepository()
): ViewModel() {

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

    private val _contador: MutableStateFlow<StateData<AgregarContadorRes>> = MutableStateFlow(StateData.None)
    val contador: StateFlow<StateData<AgregarContadorRes>> get() = _contador

    fun addContador() {
        viewModelScope.launch {
            contadorRepository.addContador()
                .catch { _contador.value = StateData.Error(it) }
                .collect {
                    _contador.value = StateData.Success(it)
                }
        }
    }

    fun editFCM(token: String, fcmReq: FcmReq) {
        viewModelScope.launch {
            loginRepository.editFCMToken(token, fcmReq)
                .catch {
                    Log.e("FCMError", it.toString())
                }
                .collect {
                    Log.e("FCMSuccess", it.toString())
                }
        }
    }
}