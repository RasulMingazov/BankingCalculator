package com.psychojean.feature.deposit.impl.domain.calculate_deposit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.domain.DepositInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal class CalculateDepositExecutor(
    private val calculateDepositUseCase: CalculateDepositUseCase,
) : CoroutineExecutor<CalculateDepositStore.Intent, Any, CalculateDepositStore.State, CalculateDepositStore.Message, Nothing>(
    mainContext = SupervisorJob() + Dispatchers.Main.immediate
) {

    sealed interface Action {
        data object InitialCalculate : Action
    }

    override fun executeIntent(
        intent: CalculateDepositStore.Intent,
        getState: () -> CalculateDepositStore.State
    ) {
        when (intent) {
            is CalculateDepositStore.Intent.CurrencyTypeChanged -> {
                dispatch(CalculateDepositStore.Message.UpdateCurrencyType(intent.currencyType))
            }

            is CalculateDepositStore.Intent.RateChanged -> {
                dispatch(CalculateDepositStore.Message.UpdateInterestRate(intent.rate))
            }

            is CalculateDepositStore.Intent.InitialDepositChanged -> {
                dispatch(CalculateDepositStore.Message.UpdateInitialDeposit(intent.deposit))
            }

            is CalculateDepositStore.Intent.PeriodChanged -> {
                dispatch(CalculateDepositStore.Message.UpdatePeriod(intent.period))
            }

            is CalculateDepositStore.Intent.PeriodTypeChanged -> {
                dispatch(CalculateDepositStore.Message.UpdatePeriodType(intent.periodType))
            }
        }
        if (intent is CalculateDepositStore.Intent.FieldUpdate) calculate(getState())
    }

    override fun executeAction(action: Any, getState: () -> CalculateDepositStore.State) {
        when (action) {
            is Action.InitialCalculate -> calculate(getState())
        }
    }

    private fun calculate(state: CalculateDepositStore.State) = scope.launch {
        val input = DepositInput(
            initialDeposit = state.initialDeposit,
            interestRate = state.interestRate,
            period = state.period,
            periodType = state.periodType
        )
        calculateDepositUseCase(input).onSuccess {
            dispatch(CalculateDepositStore.Message.UpdateCalculationResult(it))
        }
    }
}
