package com.learnbyheart.core.common

sealed interface MusicLoadResult<Value> {

    data class Success<Value>(val data: List<Value>): MusicLoadResult<Value>

    data class Error<Value>(val exception: Throwable): MusicLoadResult<Value>

}
