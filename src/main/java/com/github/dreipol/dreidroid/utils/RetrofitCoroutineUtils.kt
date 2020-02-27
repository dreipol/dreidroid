package com.github.dreipol.dreidroid.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Awaits retrofit call in coroutine. Useful for wrapping retrofit APIs in coroutines for better readability.
 */
suspend fun <T> awaitCall(call: Call<T>): Response<T> {
    return suspendCoroutine { continuation ->
        awaitCallWithContinuation(call, continuation)
    }
}

private fun <T> awaitCallWithContinuation(call: Call<T>, continuation: Continuation<Response<T>>) {
    call.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            continuation.resume(response)
        }
    })
}