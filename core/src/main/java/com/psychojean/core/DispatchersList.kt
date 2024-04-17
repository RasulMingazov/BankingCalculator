package com.psychojean.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersList {

    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher

}

class DefaultDispatchersList : DispatchersList {

    override fun io(): CoroutineDispatcher = Dispatchers.IO
    override fun default(): CoroutineDispatcher = Dispatchers.Default
    override fun ui(): CoroutineDispatcher = Dispatchers.Main
}
