package com.psychojean.core.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.psychojean.core.DefaultDispatchersList
import com.psychojean.core.DispatchersList

class DefaultCoreContainer : CoreContainer {

    override val dispatchersList: DispatchersList = DefaultDispatchersList()

    override val storeFactory: StoreFactory by lazy { DefaultStoreFactory() }
}
