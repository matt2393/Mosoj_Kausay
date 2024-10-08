package com.gotasoft.mosojkausay_mobile.view.login.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.request.PatrocinadorRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.PatrocinadorResponse
import com.gotasoft.mosojkausay_mobile.repositories.PatrocinadorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FormViewModel(private val patrocinadorRepository: PatrocinadorRepository = PatrocinadorRepository()): ViewModel() {
    private val _patro: MutableStateFlow<StateData<PatrocinadorResponse>> = MutableStateFlow(StateData.None)
    val patro: StateFlow<StateData<PatrocinadorResponse>> get() = _patro

    fun registrarPatrp(patrocinadorRequest: PatrocinadorRequest) {
        _patro.value = StateData.Loading
        viewModelScope.launch {
            patrocinadorRepository.registarPatrp(patrocinadorRequest)
                .catch {
                    _patro.value = StateData.None
                    _patro.value = StateData.Error(it)
                }
                .collect {
                    _patro.value = StateData.None
                    _patro.value = StateData.Success(it)
                }
        }
    }
}