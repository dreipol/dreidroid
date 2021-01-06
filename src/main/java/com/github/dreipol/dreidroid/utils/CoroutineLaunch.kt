package com.github.dreipol.dreidroid.utils

import kotlinx.coroutines.*
import java.util.concurrent.Executors


/**
 * Launch a coroutine in a scope that ensure execution on
 * one thread. This is useful when working with realm objects
 * as they can only be used on the thread on which they are created.
 */
public fun launchInSingleThread(block: suspend CoroutineScope.() -> Unit): Job {
    val singleThreadDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    return GlobalScope.launch(singleThreadDispatcher, block = block)
}