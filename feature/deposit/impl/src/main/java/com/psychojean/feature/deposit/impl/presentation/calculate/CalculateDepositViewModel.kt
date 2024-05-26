package com.psychojean.feature.deposit.impl.presentation.calculate

import com.psychojean.core.ComponentViewModel
import com.psychojean.core.asThousand
import com.psychojean.feature.deposit.api.CurrencyType
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.domain.validation.DepositValidationError
import com.psychojean.feature.deposit.api.domain.validation.DepositValidationUseCase
import com.psychojean.feature.deposit.api.presentation.CalculateDepositIntent
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.RoundingMode

class CalculateDepositViewModel @AssistedInject constructor(
    private val calculateDepositUseCase: CalculateDepositUseCase,
    private val depositValidationUseCase: DepositValidationUseCase
) : ComponentViewModel() {

    private val _state = MutableStateFlow(CalculateDepositUiState())
    val state = _state.asStateFlow()

    init {
        calculate()
    }

    fun accept(intent: CalculateDepositIntent) {
        when (intent) {
            is CalculateDepositIntent.InitialDepositChanged -> initialDepositChanged(intent.deposit)
            is CalculateDepositIntent.InterestRateChanged -> interestRateChanged(intent.rate)
            is CalculateDepositIntent.MonthPeriodChanged -> monthPeriodChanged(intent.period)
            is CalculateDepositIntent.CurrencyTypeChanged -> currencyTypeChanged(intent.currencyType)
        }
        if (intent is CalculateDepositIntent.FieldUpdate) validate()
    }

    private fun initialDepositChanged(deposit: String) =
        _state.update { uiState -> uiState.copy(initialDeposit = deposit.processAmountInput()) }

    private fun currencyTypeChanged(currencyType: CurrencyType) =
        _state.update { uiState -> uiState.copy(selectedCurrencyType = currencyType) }

    private fun interestRateChanged(rate: String) =
        _state.update { uiState -> uiState.copy(interestRate = rate.processInterestRateInput()) }

    private fun monthPeriodChanged(period: String) =
        _state.update { uiState -> uiState.copy(monthPeriod = period.processPeriodInput()) }

    private fun validate() = launch {
        val input = state.value.toDepositInput()
        val validationResult = depositValidationUseCase(input)
            .onSuccess { calculate(input) }
        processValidationError(validationResult.errorOrNull)
    }

    private fun calculate(input: DepositInput = state.value.toDepositInput()) = launch {
        calculateDepositUseCase(input).onSuccess {
            _state.update { uiState ->
                uiState.copy(
                    income = "${
                        it.income.setScale(2, RoundingMode.DOWN).toString().asThousand()
                    } ${state.value.selectedCurrencyType.symbol}"
                )
            }
        }
    }

    private fun processValidationError(error: DepositValidationError?) = _state.update { uiState ->
        uiState.copy(
            interestRateError = error?.interestRateError.text,
            monthPeriodError = error?.monthPeriodError.text,
            initialDepositError = error?.amountError.text
        )
    }

    @AssistedFactory
    interface Factory {
        operator fun invoke(
        ): CalculateDepositViewModel
    }
}
