package com.psychojean.feature.deposit.impl.presentation.calculate

import com.arkivanov.decompose.ComponentContext
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class DefaultCalculateDepositComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
) : CalculateDepositComponent, ComponentContext by componentContext {


    @AssistedFactory
    interface Factory : CalculateDepositComponent.Factory {
        override fun invoke(componentContext: ComponentContext): DefaultCalculateDepositComponent
    }

}