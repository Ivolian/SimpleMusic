package com.ivotai.simplemusic

class ViewState<out T>(val data: T? = null, val error: Throwable? = null) {
    fun isLoading() = data == null && error == null
    fun isSuccess() = data != null
    fun isError() = error != null
}