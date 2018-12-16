package com.ovrbach.volvomobility.util

import com.ovrbach.common.params.Params

sealed class RequestStatus<T>(val params: Params<T>) {
    class Requesting<T>(params: Params<T>) : RequestStatus<T>(params)
    class Success<T>(params: Params<T>, val data: T) : RequestStatus<T>(params)
    class Error<T>(params: Params<T>, val e: Throwable) : RequestStatus<T>(params)
    class Cancelled<T>(params: Params<T>) : RequestStatus<T>(params)
}