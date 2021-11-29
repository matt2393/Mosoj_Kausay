package com.gotasoft.mosojkausay.view.momentos_magicos.fotos_mm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.response.FotoMMResponse
import com.gotasoft.mosojkausay.repositories.FotoMMRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FotosMMViewModel(private val fotoMMRepository: FotoMMRepository = FotoMMRepository()): ViewModel() {

    private val _fotos: MutableStateFlow<StateData<ArrayList<FotoMMResponse>>> = MutableStateFlow(StateData.None)
    val fotos: StateFlow<StateData<ArrayList<FotoMMResponse>>> get() = _fotos

    fun getFotos(token: String = "",
                 id: Int) {
        _fotos.value = StateData.Loading
        viewModelScope.launch {
            fotoMMRepository.getFotosMM(token, id)
                .catch {
                    _fotos.value = StateData.None
                    _fotos.value = StateData.Error(it)
                }
                .collect {
                    _fotos.value = StateData.None
                    _fotos.value = StateData.Success(it)
                }
        }
    }
}