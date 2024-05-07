package com.psychojean.feature.deposit.api.presentation

data class CalculateDepositUiState(
    val initialDeposit: String = "350000",
    val initialDepositError: Int? = null,

    val interestRate: String = "4.3",
    val interestRateError: Int? = null,

    val monthPeriod: String = "9",
    val monthPeriodError: Int? = null,

    val income: String = ""
)
