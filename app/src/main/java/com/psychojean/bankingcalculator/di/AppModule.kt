package com.psychojean.bankingcalculator.di

import com.psychojean.core.DefaultDispatchersList
import com.psychojean.core.DispatchersList
import com.psychojean.feature.deposit.impl.DepositFeatureModule
import com.psychojean.field.impl.FieldModule
import com.psychojean.root.api.RootComponent
import com.psychojean.root.impl.DefaultRootComponent
import dagger.Binds
import dagger.Module

@Module(includes = [DepositFeatureModule::class, FieldModule::class])
interface AppModule {

    @Binds
    fun bindRootComponentFactory(impl: DefaultRootComponent.Factory): RootComponent.Factory

    @Binds
    fun bindDispatchersList(dispatchersList: DefaultDispatchersList): DispatchersList

}
