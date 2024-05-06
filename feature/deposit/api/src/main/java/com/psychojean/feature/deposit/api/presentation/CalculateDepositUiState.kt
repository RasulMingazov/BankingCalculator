package com.psychojean.feature.deposit.api.presentation

import com.psychojean.feature.deposit.api.domain.validation.ValidateDeposit

data class CalculateDepositUiState(
    val initialDeposit: String = "",
    val initialDepositError: Int? = null,

    val interestRate: String = "",
    val interestRateError: Int? = null,

    val monthPeriod: String = "",
    val monthPeriodError: Int? = null
)

fun CalculateDepositUiState.toValidateDeposit(): ValidateDeposit = ValidateDeposit(
    amount = initialDeposit.trim(),
    interest = interestRate.trim().replace(',', '.'),
    monthPeriod = monthPeriod.trim()
)
