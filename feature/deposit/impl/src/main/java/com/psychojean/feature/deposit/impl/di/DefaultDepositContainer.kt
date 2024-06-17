package com.psychojean.feature.deposit.impl.di

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.psychojean.core.CurrencyType
import com.psychojean.core.PeriodType
import com.psychojean.core.di.CoreContainer
import com.psychojean.feature.deposit.api.di.DepositContainer
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import com.psychojean.feature.deposit.impl.domain.DefaultCalculateDepositUseCase
import com.psychojean.feature.deposit.impl.domain.calculate_deposit.CalculateDepositExecutor
import com.psychojean.feature.deposit.impl.domain.calculate_deposit.CalculateDepositReducer
import com.psychojean.feature.deposit.impl.presentation.calculate.DefaultCalculateDepositComponent
import com.psychojean.field.api.di.FieldContainer
import java.math.BigInteger

class DefaultDepositContainer(
    private val coreContainer: CoreContainer,
    private val fieldContainer: FieldContainer
) : DepositContainer {

    private val calculateDepositUseCase: CalculateDepositUseCase
        get() = DefaultCalculateDepositUseCase()

    private val calculateDepositStore: CalculateDepositStore =
        object : CalculateDepositStore,
            Store<CalculateDepositStore.Intent, CalculateDepositStore.State, Nothing> by DefaultStoreFactory().create(
                name = CalculateDepositStore::class.simpleName,
                initialState = CalculateDepositStore.State(
                    interestRate = 12.0,
                    currencyType = CurrencyType.RUB,
                    currencyTypes = CurrencyType.entries,
                    periodTypes = PeriodType.entries,
                    initialDeposit = BigInteger("300000"),
                    period = 9,
                    periodType = PeriodType.MONTH
                ),
                bootstrapper = SimpleBootstrapper(CalculateDepositExecutor.Action.InitialCalculate),
                executorFactory = {
                    CalculateDepositExecutor(calculateDepositUseCase)
                },
                reducer = CalculateDepositReducer()
            ) {}

    override val calculateDepositComponentFactory: CalculateDepositComponent.Factory
        get() = DefaultCalculateDepositComponent.Factory(
            calculateDepositStore = calculateDepositStore,
            interestRateFactory = fieldContainer.interestRateComponentFactory,
            amountFactory = fieldContainer.amountComponentFactory,
            currencyTypeFactory = fieldContainer.currencyTypeComponentFactory,
            periodTypeFactory = fieldContainer.periodTypeComponentFactory,
            periodFactory = fieldContainer.periodComponentFactory
        )

}

