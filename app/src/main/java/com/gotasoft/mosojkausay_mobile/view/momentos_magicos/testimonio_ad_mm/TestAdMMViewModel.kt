package com.gotasoft.mosojkausay_mobile.view.momentos_magicos.testimonio_ad_mm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.response.TestimonioAdResponse
import com.gotasoft.mosojkausay_mobile.repositories.TestAdMMRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TestAdMMViewModel(private val testAdMMRepository: TestAdMMRepository = TestAdMMRepository()): ViewModel() {

    private val _testAd: MutableStateFlow<StateData<ArrayList<TestimonioAdResponse>>> = MutableStateFlow(StateData.None)
    val testAd: StateFlow<StateData<ArrayList<TestimonioAdResponse>>> get() = _testAd

    fun getTestAd(token: String = "",
                  id: Int) {
        _testAd.value = StateData.Loading
        viewModelScope.launch {
            testAdMMRepository.getTestAd(token, id)
                .catch {
                    _testAd.value = StateData.None
                    _testAd.value = StateData.Error(it)
                }
                .collect {
                    _testAd.value = StateData.None
                    _testAd.value = StateData.Success(it)
                }
        }
    }
}