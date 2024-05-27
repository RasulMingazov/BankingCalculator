package com.psychojean.feature.deposit.impl.presentation.calculate

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.psychojean.core.asThousand
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.RoundingMode

internal class DefaultCalculateDepositComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    private val calculateDepositStore: CalculateDepositStore,
) : CalculateDepositComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getOrCreate { calculateDepositStore }

    override val state: Flow<CalculateDepositUiState> = store.states.map {
        CalculateDepositUiState(
            initialDeposit = it.initialDeposit,
            initialDepositError = it.initialDepositError.text,
            period = it.period,
            periodError = it.periodError?.monthPeriodValidationError.text
                ?: it.periodError?.yearPeriodValidationError.text,
            interestRate = it.interestRate,
            interestRateError = it.interestRateError.text,
            currencyTypes = it.currencyTypes.toImmutableList(),
            selectedCurrencyName = it.selectedCurrencyType.name,
            selectedPeriodName = it.selectedPeriodType.name,
            periodTypes = it.periodTypes.toImmutableList(),
            incomeRatio = it.incomeRatio,
            depositAmount = "${
                it.depositAmount?.setScale(2, RoundingMode.DOWN).toString().asThousand()
            } ${it.selectedCurrencyType.symbol}",
            totalValue = "${
                it.totalValue?.setScale(2, RoundingMode.DOWN).toString().asThousand()
            } ${it.selectedCurrencyType.symbol}",
            income = "${
                it.income?.setScale(2, RoundingMode.DOWN).toString().asThousand()
            } ${it.selectedCurrencyType.symbol}"
        )
    }

    override fun accept(intent: CalculateDepositStore.Intent) {
        store.accept(intent)
    }

    @AssistedFactory
    interface Factory : CalculateDepositComponent.Factory {
        override fun invoke(componentContext: ComponentContext): DefaultCalculateDepositComponent
    }

}