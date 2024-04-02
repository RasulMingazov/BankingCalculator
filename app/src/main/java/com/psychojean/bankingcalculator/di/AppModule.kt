package com.psychojean.bankingcalculator.di

import com.psychojean.core.api.DefaultDispatchersList
import com.psychojean.core.api.DispatchersList
import com.psychojean.feature.deposit.impl.DepositFeatureModule
import com.psychojean.root.api.RootComponent
import com.psychojean.root.impl.DefaultRootComponent
import dagger.Binds
import dagger.Module

@Module(includes = [DepositFeatureModule::class])
interface AppModule {

    @Binds
    fun bindRootComponentFactory(impl: DefaultRootComponent.Factory): RootComponent.Factory

    @Binds
    fun bindDispatchersList(dispatchersList: DefaultDispatchersList): DispatchersList

}
