package com.gotasoft.mosojkausay.view.corres.planilla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.response.PlanillaResponse
import com.gotasoft.mosojkausay.repositories.PlanillaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlanillaViewModel(private val planillaRepository: PlanillaRepository = PlanillaRepository()): ViewModel() {
    private val _planilla: MutableStateFlow<StateData<ArrayList<PlanillaResponse>>> = MutableStateFlow(StateData.None)
    val planilla: StateFlow<StateData<ArrayList<PlanillaResponse>>> get() = _planilla

    fun getPlanilla(token: String, activo: Int, tipo: String) {
        _planilla.value = StateData.Loading
        viewModelScope.launch {
            planillaRepository.getPlanillas(token, activo, tipo)
                .catch {
                    _planilla.value = StateData.None
                    _planilla.value = StateData.Error(it)
                }
                .collect {
                    _planilla.value = StateData.None
                    _planilla.value = StateData.Success(it)
                }
        }
    }
}