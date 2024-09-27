package com.gotasoft.mosojkausay_mobile.view.villa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.response.VillaResponse
import com.gotasoft.mosojkausay_mobile.repositories.VillaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class VillaViewModel(private val villaRepository: VillaRepository = VillaRepository()) : ViewModel() {
    private val _villaState = MutableStateFlow<StateData<ArrayList<VillaResponse>>>(StateData.None)
    val villaState: StateFlow<StateData<ArrayList<VillaResponse>>> get() = _villaState

    fun getVillas() {
        viewModelScope.launch {
            _villaState.value = StateData.Loading
            villaRepository.getVillas()
                .catch { _villaState.value = StateData.Error(it) }
                .collect { _villaState.value = StateData.Success(it) }
        }
    }
}