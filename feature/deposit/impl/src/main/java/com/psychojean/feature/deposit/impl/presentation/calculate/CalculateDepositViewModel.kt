package com.psychojean.feature.deposit.impl.presentation.calculate

import com.psychojean.core.ComponentViewModel
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculateDepositViewModel @AssistedInject constructor(
): ComponentViewModel() {

    private val _state = MutableStateFlow(CalculateDepositUiState())
    val state = _state.asStateFlow()

    fun onInitialDepositChange(text: String) {
        _state.update { uiState -> uiState.copy(initialDeposit = text, initialDepositError = null) }
    }

    fun onInterestRateChange(interestRate: String) {
        _state.update { uiState -> uiState.copy(interestRate = interestRate, interestRateError = null) }
    }

    fun onMonthPeriodChange(monthPeriod: String) {
        _state.update { uiState -> uiState.copy(monthPeriod = monthPeriod, monthPeriodError = null) }
    }

    @AssistedFactory
    interface Factory {
        operator fun invoke(
        ): CalculateDepositViewModel
    }
}