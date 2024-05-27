package com.psychojean.feature.deposit.api.domain

import com.psychojean.core.Error
import com.psychojean.core.RootResult
import java.math.BigDecimal

interface CalculateDepositUseCase {

    suspend operator fun invoke(depositInput: DepositInput): RootResult<DepositOutput, Error>

}

object CalculationError: Error

data class DepositInput(
    val initialDeposit: String,
    val interestRate: String,
    val monthPeriod: String
)

data class DepositOutput(
    val depositAmount: BigDecimal,
    val income: BigDecimal,
    val totalValue: BigDecimal,
    val incomeRatio: Float
)
