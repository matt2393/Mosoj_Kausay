package com.gotasoft.mosojkausay.view.corres.list_corres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.request.ValidarCorresRequest
import com.gotasoft.mosojkausay.model.entities.response.*
import com.gotasoft.mosojkausay.repositories.CorresRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListCorresViewModel(private val corresRepository: CorresRepository = CorresRepository()): ViewModel() {
    private val _reply: MutableStateFlow<StateData<ArrayList<ReplyResponse>>> = MutableStateFlow(StateData.None)
    val reply: StateFlow<StateData<ArrayList<ReplyResponse>>> get() = _reply

    fun getMisReply(token: String, value: String, planillaId: String, validado: String) {
        _reply.value = StateData.Loading
        viewModelScope.launch {
            corresRepository.getMisCorresReply(token = token,
                value = value, planilla_id = planillaId,
                validacion = validado).catch {
                _reply.value = StateData.None
                _reply.value = StateData.Error(it)
            }.collect {
                _reply.value = StateData.None
                _reply.value = StateData.Success(it)
            }
        }
    }

    fun getReply(token: String, value: String, planillaId: String, validado: String) {
        _reply.value = StateData.Loading
        viewModelScope.launch {
            corresRepository.getCorresReply(token = token,
                value = value, planilla_id = planillaId,
                validacion = validado).catch {
                _reply.value = StateData.None
                _reply.value = StateData.Error(it)
            }.collect {
                _reply.value = StateData.None
                _reply.value = StateData.Success(it)
            }
        }
    }



    private val _welcome: MutableStateFlow<StateData<ArrayList<WelcomeResponse>>> = MutableStateFlow(StateData.None)
    val welcome: StateFlow<StateData<ArrayList<WelcomeResponse>>> get() = _welcome

    fun getMisWelcome(token: String, value: String, planillaId: String, validado: String) {
        _welcome.value = StateData.Loading
        viewModelScope.launch {
            corresRepository.getMisCorresWelcome(token = token,
                value = value, planilla_id = planillaId,
                validacion = validado).catch {
                _welcome.value = StateData.None
                _welcome.value = StateData.Error(it)
            }.collect {
                _welcome.value = StateData.None
                _welcome.value = StateData.Success(it)
            }
        }
    }

    fun getWelcome(token: String, value: String, planillaId: String, validado: String) {
        _welcome.value = StateData.Loading
        viewModelScope.launch {
            corresRepository.getCorresWelcome(token = token,
                value = value, planilla_id = planillaId,
                validacion = validado).catch {
                _welcome.value = StateData.None
                _welcome.value = StateData.Error(it)
            }.collect {
                _welcome.value = StateData.None
                _welcome.value = StateData.Success(it)
            }
        }
    }


    private val _dfc: MutableStateFlow<StateData<ArrayList<DfcResponse>>> = MutableStateFlow(StateData.None)
    val dfc: StateFlow<StateData<ArrayList<DfcResponse>>> get() = _dfc

    fun getMisDfc(token: String, value: String, planillaId: String, validado: String) {
        _welcome.value = StateData.Loading
        viewModelScope.launch {
            corresRepository.getMisCorresDfc(token = token,
                value = value, planilla_id = planillaId,
                validacion = validado).catch {
                _dfc.value = StateData.None
                _dfc.value = StateData.Error(it)
            }.collect {
                _dfc.value = StateData.None
                _dfc.value = StateData.Success(it)
            }
        }
    }

    fun getDfc(token: String, value: String, planillaId: String, validado: String) {
        _welcome.value = StateData.Loading
        viewModelScope.launch {
            corresRepository.getCorresDfc(token = token,
                value = value, planilla_id = planillaId,
                validacion = validado).catch {
                _dfc.value = StateData.None
                _dfc.value = StateData.Error(it)
            }.collect {
                _dfc.value = StateData.None
                _dfc.value = StateData.Success(it)
            }
        }
    }



    private val _unavailable: MutableStateFlow<StateData<ArrayList<UnavailableResponse>>> = MutableStateFlow(StateData.None)
    val unavailable: StateFlow<StateData<ArrayList<UnavailableResponse>>> get() = _unavailable

    fun getMisUnavailable(token: String, value: String, planillaId: String, validado: String) {
        _welcome.value = StateData.Loading
        viewModelScope.launch {
            corresRepository.getMisCorresUnavailable(token = token,
                value = value, planilla_id = planillaId,
                validacion = validado).catch {
                _unavailable.value = StateData.None
                _unavailable.value = StateData.Error(it)
            }.collect {
                _unavailable.value = StateData.None
                _unavailable.value = StateData.Success(it)
            }
        }
    }

    fun getUnavailable(token: String, value: String, planillaId: String, validado: String) {
        _welcome.value = StateData.Loading
        viewModelScope.launch {
            corresRepository.getCorresUnavailable(token = token,
                value = value, planilla_id = planillaId,
                validacion = validado).catch {
                _unavailable.value = StateData.None
                _unavailable.value = StateData.Error(it)
            }.collect {
                _unavailable.value = StateData.None
                _unavailable.value = StateData.Success(it)
            }
        }
    }

    /**
     * validar
     */

    private val _validado: MutableStateFlow<StateData<ValidarCorresResponse>> = MutableStateFlow(StateData.None)
    val validado: StateFlow<StateData<ValidarCorresResponse>> get() = _validado

    fun validarCorres(token: String, id: String, validarCorresRequest: ValidarCorresRequest) {
        _validado.value = StateData.Loading
        viewModelScope.launch {
            corresRepository.validarCorres(token, id, validarCorresRequest)
                .catch {
                    _validado.value = StateData.None
                    _validado.value = StateData.Error(it)
                }
                .collect {
                    _validado.value = StateData.None
                    _validado.value = StateData.Success(it)
                }
        }
    }
}