package com.gotasoft.mosojkausay_mobile.view.login.init

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.response.ContadorRes
import com.gotasoft.mosojkausay_mobile.model.entities.response.SlideResponse
import com.gotasoft.mosojkausay_mobile.repositories.ContadorRepository
import com.gotasoft.mosojkausay_mobile.repositories.SlideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class InitViewModel(
    private val slideRepository: SlideRepository = SlideRepository(),
    private var contadorRepository: ContadorRepository = ContadorRepository()
): ViewModel() {
    private val _slides: MutableStateFlow<StateData<ArrayList<SlideResponse>>> = MutableStateFlow(StateData.None)
    val slides: StateFlow<StateData<ArrayList<SlideResponse>>> get() = _slides

    fun getSlides() {
        _slides.value = StateData.Loading
        viewModelScope.launch {
            slideRepository.getSlides()
                .catch { _slides.value = StateData.Error(it) }
                .collect { _slides.value = StateData.Success(it) }
        }
    }

    private val _contador: MutableStateFlow<StateData<ContadorRes>> = MutableStateFlow(StateData.None)
    val contador: StateFlow<StateData<ContadorRes>> get() = _contador

    fun getContador() {
        _contador.value = StateData.Loading
        viewModelScope.launch {
            contadorRepository.getContador()
                .catch { _contador.value = StateData.Error(it) }
                .collect { _contador.value = StateData.Success(it) }
        }
    }
}