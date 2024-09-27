package com.gotasoft.mosojkausay_mobile

sealed class ResultData<out R> {
    data class Success<T>(val data: T): ResultData<T>()
    data class Error(val error: Exception): ResultData<Nothing>()

}