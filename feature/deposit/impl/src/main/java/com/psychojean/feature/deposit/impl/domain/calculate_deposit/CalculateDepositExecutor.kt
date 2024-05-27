package com.psychojean.feature.deposit.impl.domain.calculate_deposit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.psychojean.feature.deposit.api.CurrencyType
import com.psychojean.feature.deposit.api.PeriodType
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.period.PeriodValidationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal class CalculateDepositExecutor(
    private val calculateDepositUseCase: CalculateDepositUseCase,
    private val amountValidationUseCase: AmountValidationUseCase,
    private val interestRateValidationUseCase: InterestValidationUseCase,
    private val periodValidationUseCase: PeriodValidationUseCase
) : CoroutineExecutor<CalculateDepositStore.Intent, Any, CalculateDepositStore.State, CalculateDepositStore.Message, CalculateDepositStore.Label>(
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
            is CalculateDepositStore.Intent.InitialDepositChanged -> onInitialDepositChanged(
                intent.deposit,
                getState
            )

            is CalculateDepositStore.Intent.InterestRateChanged -> onInterestRateChanged(
                intent.rate,
                getState
            )

            is CalculateDepositStore.Intent.PeriodChanged -> onPeriodChanged(
                intent.period,
                getState
            )
            is  CalculateDepositStore.Intent.PeriodTypeChanged -> onPeriodTypeChanged(
                intent.periodType, getState
            )

            is CalculateDepositStore.Intent.CurrencyTypeChanged -> onCurrencyTypeChanged(intent.currencyType)
        }
    }

    override fun executeAction(action: Any, getState: () -> CalculateDepositStore.State) {
        when (action) {
            is Action.InitialCalculate -> calculate(getState)
        }
    }

    private fun onInitialDepositChanged(
        newDeposit: String,
        getState: () -> CalculateDepositStore.State
    ) = scope.launch {
        val preprocessDeposit = newDeposit.filter { it.isDigit() }.take(10)
        dispatch(CalculateDepositStore.Message.UpdateInitialDeposit(preprocessDeposit))
        amountValidationUseCase(preprocessDeposit).onSuccess {
            dispatch(CalculateDepositStore.Message.UpdateInitialDepositError(null))
            calculate(getState)
        }.onFailure {
            dispatch(CalculateDepositStore.Message.UpdateInitialDepositError(it))
        }
    }

    private fun onInterestRateChanged(
        newInterestRate: String,
        getState: () -> CalculateDepositStore.State
    ) = scope.launch {
        val preprocessInterestRate = newInterestRate.take(4)
        dispatch(CalculateDepositStore.Message.UpdateInterestRate(preprocessInterestRate))
        interestRateValidationUseCase(preprocessInterestRate).onSuccess {
            dispatch(CalculateDepositStore.Message.UpdateInterestRateError(null))
            calculate(getState)
        }.onFailure {
            dispatch(CalculateDepositStore.Message.UpdateInterestRateError(it))
        }
    }

    private fun onPeriodChanged(
        newPeriod: String,
        getState: () -> CalculateDepositStore.State
    ) = scope.launch {
        val preprocessPeriod = newPeriod.take(4)
        dispatch(CalculateDepositStore.Message.UpdatePeriod(preprocessPeriod))
        periodValidationUseCase(preprocessPeriod, getState().selectedPeriodType).onSuccess {
            dispatch(CalculateDepositStore.Message.UpdatePeriodError(null))
            calculate(getState)
        }.onFailure {
            dispatch(CalculateDepositStore.Message.UpdatePeriodError(it))
        }
    }

    private fun onPeriodTypeChanged(
        newPeriodType: PeriodType,
        getState: () -> CalculateDepositStore.State
    ) = scope.launch {
        dispatch(CalculateDepositStore.Message.UpdatePeriodType(newPeriodType))
        periodValidationUseCase(getState().period, newPeriodType).onSuccess {
            dispatch(CalculateDepositStore.Message.UpdatePeriodError(null))
            calculate(getState)
        }.onFailure {
            dispatch(CalculateDepositStore.Message.UpdatePeriodError(it))
        }
    }

    private fun onCurrencyTypeChanged(newCurrencyType: CurrencyType) = scope.launch {
        dispatch(CalculateDepositStore.Message.UpdateSelectedCurrencyType(newCurrencyType))
    }

    private fun calculate(getState: () -> CalculateDepositStore.State) = scope.launch {
        val state = getState()
        if (!state.isAllFieldsCorrect) return@launch
        val input = DepositInput(
            initialDeposit = state.initialDeposit.trim(),
            interestRate = state.interestRate.trim(),
            period = state.period.trim(),
            periodType = state.selectedPeriodType
        )
        calculateDepositUseCase(input).onSuccess {
            dispatch(CalculateDepositStore.Message.UpdateCalculationResult(it))
        }
    }
}
