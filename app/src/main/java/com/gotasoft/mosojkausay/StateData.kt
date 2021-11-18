package com.gotasoft.mosojkausay

sealed class StateData<out R> {
    data class Success<T>(val data: T): StateData<T>()
    data class Error(val error: Throwable): StateData<Nothing>()
    object Loading : StateData<Nothing>()
    object None: StateData<Nothing>()
}