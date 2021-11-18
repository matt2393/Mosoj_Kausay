package com.gotasoft.mosojkausay.view.login.init

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.response.SlideResponse
import com.gotasoft.mosojkausay.repositories.SlideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class InitViewModel(private val slideRepository: SlideRepository = SlideRepository()): ViewModel() {
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
}