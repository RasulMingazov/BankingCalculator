package com.psychojean.feature.deposit.impl.presentation.calculate

import com.psychojean.core.ComponentViewModel
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.domain.validation.DepositValidationError
import com.psychojean.feature.deposit.api.domain.validation.DepositValidationUseCase
import com.psychojean.feature.deposit.api.presentation.CalculateDepositIntent
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState
import com.psychojean.feature.deposit.api.presentation.toValidateDeposit
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalculateDepositViewModel @AssistedInject constructor(
    private val calculateDepositValidationUseCase: DepositValidationUseCase,
    private val calculateDepositUseCase: CalculateDepositUseCase
) : ComponentViewModel() {

    private val _state = MutableStateFlow(
        CalculateDepositUiState(
            initialDeposit = "350000",
            interestRate = "4",
            monthPeriod = "9"
        )
    )
    val state = _state.asStateFlow()

    init {
        validate()
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

    private fun interestRateChanged(interestRate: String) {
        _state.update { uiState -> uiState.copy(interestRate = interestRate) }
        validate()
    }

    private fun monthPeriodChanged(monthPeriod: String) {
        _state.update { uiState -> uiState.copy(monthPeriod = monthPeriod) }
        validate()
    }

    private fun validate() = launch {
        val validationResult =
            calculateDepositValidationUseCase(state.value.toValidateDeposit()).onSuccess(::calculate)
        processValidationError(validationResult.errorOrNull)
    }

    private fun calculate(depositInput: DepositInput) = launch {
        calculateDepositUseCase(depositInput).onSuccess {
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
