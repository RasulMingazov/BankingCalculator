package com.psychojean.feature.deposit.api.domain

import com.psychojean.core.Error
import com.psychojean.core.RootResult
import com.psychojean.core.PeriodType
import java.math.BigDecimal
import java.math.BigInteger

interface CalculateDepositUseCase {

    suspend operator fun invoke(depositInput: DepositInput): RootResult<DepositOutput, Error>

}

object CalculationError: Error

data class DepositInput(
    val initialDeposit: BigInteger,
    val interestRate: Double,
    val period: Int,
    val periodType: PeriodType
)

data class DepositOutput(
    val depositAmount: BigDecimal,
    val income: BigDecimal,
    val totalValue: BigDecimal,
    val incomeRatio: Float
)
