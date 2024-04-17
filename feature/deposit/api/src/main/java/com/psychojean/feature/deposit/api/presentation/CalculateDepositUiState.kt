package com.psychojean.feature.deposit.api.presentation

data class CalculateDepositUiState(
    val initialDeposit: String = "",
    val initialDepositError: Int? = null,

    val interestRate: String = "",
    val interestRateError: Int? = null,

    val monthPeriod: String = "",
    val monthPeriodError: Int? = null
)