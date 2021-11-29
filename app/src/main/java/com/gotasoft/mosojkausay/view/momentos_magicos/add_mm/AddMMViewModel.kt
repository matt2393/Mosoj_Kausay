package com.gotasoft.mosojkausay.view.momentos_magicos.add_mm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.request.MMRequest
import com.gotasoft.mosojkausay.model.entities.response.MMResponse
import com.gotasoft.mosojkausay.repositories.MMRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddMMViewModel(private val mmRepository: MMRepository = MMRepository()): ViewModel() {

    private val _mm: MutableStateFlow<StateData<MMResponse>> = MutableStateFlow(StateData.None)
    val mm: StateFlow<StateData<MMResponse>> get() = _mm

    fun addMM(token: String = "",
              mmRequest: MMRequest) {
        _mm.value = StateData.Loading
        viewModelScope.launch {
            mmRepository.addMM(token, mmRequest)
                .catch {
                    _mm.value = StateData.None
                    _mm.value = StateData.Error(it)
                }
                .collect {
                    _mm.value = StateData.None
                    _mm.value = StateData.Success(it)
                }
        }
    }
}