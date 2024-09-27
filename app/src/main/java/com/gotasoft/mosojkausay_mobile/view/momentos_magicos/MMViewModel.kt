package com.gotasoft.mosojkausay_mobile.view.momentos_magicos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.response.MMResponse
import com.gotasoft.mosojkausay_mobile.repositories.MMRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MMViewModel(private val mmRepository: MMRepository = MMRepository()): ViewModel() {

    private val _mm: MutableStateFlow<StateData<ArrayList<MMResponse>>> = MutableStateFlow(StateData.None)
    val mm: StateFlow<StateData<ArrayList<MMResponse>>> get() = _mm

    fun getMM(token: String = "",
              value: String = "",
              items: Int = 10,
              page: Int = 1) {
        _mm.value = StateData.Loading
        viewModelScope.launch {
            mmRepository.getMM(token, value, items, page)
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