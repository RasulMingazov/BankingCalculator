package com.psychojean.bankingcalculator.di

import com.psychojean.root.api.RootComponent
import com.psychojean.root.impl.DefaultRootComponent
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @Binds
    fun bindRootComponentFactory(impl: DefaultRootComponent.Factory): RootComponent.Factory

}
