package com.psychojean.core.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.psychojean.core.DispatchersList

interface CoreContainer {

    val dispatchersList: DispatchersList

    val storeFactory: StoreFactory

}
