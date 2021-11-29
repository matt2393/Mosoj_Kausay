package com.gotasoft.mosojkausay.view.momentos_magicos.testimonio_ad_mm.test_ad_add_mm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.request.TestimonioAdRequest
import com.gotasoft.mosojkausay.model.entities.response.TestimonioAdResponse
import com.gotasoft.mosojkausay.repositories.TestAdMMRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TestAdMMAddViewModel(private val testAdMMRepository: TestAdMMRepository = TestAdMMRepository()): ViewModel() {

    private val _testAd: MutableStateFlow<StateData<TestimonioAdResponse>> = MutableStateFlow(
        StateData.None)
    val testAd: StateFlow<StateData<TestimonioAdResponse>> get() = _testAd

    fun addTestAd(token: String = "",
                  testimonioAdRequest: TestimonioAdRequest) {
        _testAd.value = StateData.Loading
        viewModelScope.launch {
            testAdMMRepository.addTestAd(token, testimonioAdRequest)
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