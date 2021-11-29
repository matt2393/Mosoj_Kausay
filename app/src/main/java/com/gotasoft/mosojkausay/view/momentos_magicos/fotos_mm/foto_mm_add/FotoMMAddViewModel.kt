package com.gotasoft.mosojkausay.view.momentos_magicos.fotos_mm.foto_mm_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.model.entities.response.FotoMMAddResponse
import com.gotasoft.mosojkausay.model.entities.response.FotoMMResponse
import com.gotasoft.mosojkausay.repositories.FotoMMRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

class FotoMMAddViewModel(private val fotoMMRepository: FotoMMRepository = FotoMMRepository()): ViewModel() {

    private val _foto: MutableStateFlow<StateData<FotoMMAddResponse>> = MutableStateFlow(
        StateData.None)
    val foto: StateFlow<StateData<FotoMMAddResponse>> get() = _foto

    fun addFotos(token: String = "",
                 desc: String = "",
                 mmId: Int = 0,
                 foto: File) {
        _foto.value = StateData.Loading
        viewModelScope.launch {
            fotoMMRepository.addFotoMM(token, desc, mmId, foto)
                .catch {
                    _foto.value = StateData.None
                    _foto.value = StateData.Error(it)
                }
                .collect {
                    _foto.value = StateData.None
                    _foto.value = StateData.Success(it)
                }
        }
    }
}