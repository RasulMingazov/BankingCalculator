package com.psychojean.feature.deposit.impl.presentation.calculate

import com.psychojean.core.ComponentViewModel
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
        }
    }

    private fun initialDepositChanged(deposit: String) = launch {
        _state.update { uiState -> uiState.copy(initialDeposit = deposit) }
        validate()
    }

    private fun interestRateChanged(rate: String) = launch {
        _state.update { uiState -> uiState.copy(interestRate = rate) }
        validate()
    }

    private fun monthPeriodChanged(period: String) = launch {
        _state.update { uiState -> uiState.copy(monthPeriod = period) }
        validate()
    }

    private fun validate() = launch {
        val input = state.value.toDepositInput()
        val validationResult = depositValidationUseCase(input)
            .onSuccess { calculate(input) }
        processValidationError(validationResult.errorOrNull)
    }

    private fun calculate(input: DepositInput = state.value.toDepositInput()) = launch {
        calculateDepositUseCase(input).onSuccess {
            _state.update { uiState -> uiState.copy(income = it.income.toPlainString()) }
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
