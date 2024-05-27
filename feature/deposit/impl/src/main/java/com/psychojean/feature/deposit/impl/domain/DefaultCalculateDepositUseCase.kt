package com.psychojean.feature.deposit.impl.domain

import com.psychojean.core.Error
import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.domain.CalculationError
import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.domain.DepositOutput
import javax.inject.Inject

internal class DefaultCalculateDepositUseCase @Inject constructor() : CalculateDepositUseCase {

    override suspend fun invoke(depositInput: DepositInput): RootResult<DepositOutput, Error> =
        try {
            val interestRate = depositInput.interestRate.toDouble() / 100
            val period: Double = depositInput.monthPeriod.toDouble() / 12.0
            val initial = depositInput.initialDeposit.toBigDecimal()
            val income = initial * interestRate.toBigDecimal() * period.toBigDecimal()
            val total = income + initial
            val ratio = (income / total).toFloat()
            RootResult.Success(
                DepositOutput(
                    depositAmount = initial,
                    income = income,
                    totalValue = total,
                    incomeRatio = ratio
                )
            )
        } catch (exception: Exception) {
            RootResult.Failure(CalculationError)
        }
}