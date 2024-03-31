package com.psychojean.feature.deposit.impl.presentation

import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import com.psychojean.feature.deposit.impl.presentation.calculate.DefaultCalculateDepositComponent
import dagger.Binds
import dagger.Module

@Module
internal interface DepositPresentationModule {

    @Binds
    fun bindCalculateDepositFactory(calculateDepositComponent: DefaultCalculateDepositComponent.Factory): CalculateDepositComponent.Factory

}
