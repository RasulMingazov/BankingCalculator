package com.psychojean.core.di

import com.psychojean.core.DefaultDispatchersList
import com.psychojean.core.DispatchersList

class DefaultCoreContainer : CoreContainer {

    override val dispatchersList: DispatchersList = DefaultDispatchersList()
}
