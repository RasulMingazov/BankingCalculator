package com.psychojean.feature.deposit.impl.di

import com.psychojean.core.di.CoreContainer
import com.psychojean.feature.deposit.api.di.DepositContainer
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import com.psychojean.feature.deposit.impl.domain.DefaultCalculateDepositUseCase
import com.psychojean.feature.deposit.impl.presentation.calculate.DefaultCalculateDepositComponent
import com.psychojean.field.api.di.FieldContainer

class DefaultDepositContainer(
    private val coreContainer: CoreContainer,
    private val fieldContainer: FieldContainer
) : DepositContainer {

    private val calculateDepositUseCase: CalculateDepositUseCase
        get() = DefaultCalculateDepositUseCase()

    override val calculateDepositComponentFactory: CalculateDepositComponent.Factory
        get() = DefaultCalculateDepositComponent.Factory(
            storeFactory = coreContainer.storeFactory,
            calculateDepositUseCase = calculateDepositUseCase,
            interestRateFactory = fieldContainer.interestRateComponentFactory,
            amountFactory = fieldContainer.amountComponentFactory,
            currencyTypeFactory = fieldContainer.currencyTypeComponentFactory,
            periodTypeFactory = fieldContainer.periodTypeComponentFactory,
            periodFactory = fieldContainer.periodComponentFactory
        )

}

