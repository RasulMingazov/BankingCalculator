package com.psychojean.feature.deposit.impl.presentation.calculate

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.psychojean.core.asThousand
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState
import com.psychojean.field.api.amount.AmountComponent
import com.psychojean.field.api.currency_type.CurrencyTypeComponent
import com.psychojean.field.api.interest_rate.InterestRateComponent
import com.psychojean.field.api.period.PeriodComponent
import com.psychojean.field.api.period_type.PeriodTypeComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.math.RoundingMode

internal class DefaultCalculateDepositComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    private val calculateDepositStore: CalculateDepositStore,
    interestRateFactory: InterestRateComponent.Factory,
    amountFactory: AmountComponent.Factory,
    currencyTypeFactory: CurrencyTypeComponent.Factory,
    periodFactory: PeriodComponent.Factory,
    periodTypeFactory: PeriodTypeComponent.Factory,
) : CalculateDepositComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getOrCreate { calculateDepositStore }

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    override val interestRateComponent: InterestRateComponent = interestRateFactory(
        componentContext = childContext("INTEREST_RATE"),
        rate = store.state.interestRate
    )

    override val amountComponent: AmountComponent = amountFactory(
        componentContext = childContext("AMOUNT"),
        amount = store.state.initialDeposit
    )

    override val currencyTypeComponent: CurrencyTypeComponent = currencyTypeFactory(
        componentContext = childContext("CURRENCY_TYPE"),
        currencyType = store.state.currencyType,
        currencyTypes = store.state.currencyTypes
    )

    override val periodComponent: PeriodComponent = periodFactory(
        componentContext = childContext("PERIOD"),
        period = store.state.period,
        periodType = store.state.periodType
    )

    override val periodTypeComponent: PeriodTypeComponent = periodTypeFactory(
        componentContext = childContext("PERIOD_TYPE"),
        periodType = store.state.periodType,
        periodTypes = store.state.periodTypes
    )

    init {
        scope.launch {
            interestRateComponent.value.collect { rate ->
                accept(CalculateDepositStore.Intent.RateChanged(rate))
            }
        }
        scope.launch {
            amountComponent.value.collect { amount ->
                accept(CalculateDepositStore.Intent.InitialDepositChanged(amount))
            }
        }
        scope.launch {
            currencyTypeComponent.value.collect { currencyType ->
                accept(CalculateDepositStore.Intent.CurrencyTypeChanged(currencyType))
            }
        }
        scope.launch {
            periodComponent.value.collect { period ->
                accept(CalculateDepositStore.Intent.PeriodChanged(period))
            }
        }
        scope.launch {
            periodTypeComponent.value.collect { periodType ->
                periodComponent.onPeriodTypeSelected(periodType)
                accept(CalculateDepositStore.Intent.PeriodTypeChanged(periodType))
            }
        }

        lifecycle.doOnDestroy { scope.cancel() }
    }

    override val state: Flow<CalculateDepositUiState> = store.states.map {
        CalculateDepositUiState(
            incomeRatio = it.incomeRatio,
            depositAmount = "${
                it.depositAmount?.setScale(2, RoundingMode.DOWN).toString().asThousand()
            } ${it.currencyType.symbol}",
            totalValue = "${
                it.totalValue?.setScale(2, RoundingMode.DOWN).toString().asThousand()
            } ${it.currencyType.symbol}",
            income = "${
                it.income?.setScale(2, RoundingMode.DOWN).toString().asThousand()
            } ${it.currencyType.symbol}"
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