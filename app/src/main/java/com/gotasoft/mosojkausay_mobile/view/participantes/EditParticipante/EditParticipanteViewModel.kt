package com.gotasoft.mosojkausay_mobile.view.participantes.EditParticipante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.request.ParticipanteRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.ResponseGeneric
import com.gotasoft.mosojkausay_mobile.model.entities.response.SuccessRes
import com.gotasoft.mosojkausay_mobile.repositories.ParticipanteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File

class EditParticipanteViewModel(
    private val participanteRepository: ParticipanteRepository = ParticipanteRepository()
): ViewModel() {
    private val _savePart: MutableStateFlow<StateData<ResponseGeneric>> = MutableStateFlow(StateData.None)
    val savePart: StateFlow<StateData<ResponseGeneric>> get() = _savePart


    fun editParticipante(participanteRequest: ParticipanteRequest) {
        viewModelScope.launch {
            _savePart.value = StateData.Loading
            participanteRepository.editParticipante(participanteRequest)
                .catch { _savePart.value = StateData.Error(it) }
                .collect { _savePart.value = StateData.Success(it) }
        }
    }

    private val _savePhoto: MutableStateFlow<StateData<SuccessRes>> = MutableStateFlow(StateData.None)
    val savePhoto: StateFlow<StateData<SuccessRes>> get() = _savePhoto

    fun savePhotoPart(childNumber: String, type: String, photo: File) {
        _savePhoto.value = StateData.Loading
        viewModelScope.launch {
            participanteRepository.savePhoto(childNumber, type, photo)
                .catch {
                    _savePhoto.value = StateData.Error(it)
                    _savePhoto.value = StateData.None
                }
                .collect {
                    _savePhoto.value = StateData.Success(it)
                    _savePhoto.value = StateData.None
                }
        }
    }
}