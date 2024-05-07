package com.psychojean.feature.deposit.impl.presentation.calculate

import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState

fun String.toAmountInput() = trim()
fun String.toInterestRateInput() = trim().replace(',', '.')
fun String.toPeriodInput() = trim()

fun CalculateDepositUiState.toDepositInput(): DepositInput = DepositInput(
    initialDeposit = initialDeposit.toAmountInput(),
    interestRate = interestRate.toInterestRateInput(),
    monthPeriod = monthPeriod.toPeriodInput()
)
