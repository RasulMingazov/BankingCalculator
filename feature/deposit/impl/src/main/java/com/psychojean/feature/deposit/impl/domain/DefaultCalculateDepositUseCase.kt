package com.psychojean.feature.deposit.impl.domain

import com.psychojean.core.Error
import com.psychojean.core.RootResult
import com.psychojean.core.PeriodType
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.domain.CalculationError
import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.domain.DepositOutput
import javax.inject.Inject

internal class DefaultCalculateDepositUseCase @Inject constructor() : CalculateDepositUseCase {

    override suspend fun invoke(depositInput: DepositInput): RootResult<DepositOutput, Error> =
        try {
            val interestRate = depositInput.interestRate / 100
            val period =
                if (depositInput.periodType == PeriodType.MONTH) depositInput.period.toDouble() / 12.0 else depositInput.period.toDouble()
            val initial = depositInput.initialDeposit
            val income = initial.toBigDecimal() * interestRate.toBigDecimal() * period.toBigDecimal()
            val total = income + initial.toBigDecimal()
            val ratio = (income / total).toFloat()
            RootResult.Success(
                DepositOutput(
                    depositAmount = initial.toBigDecimal(),
                    income = income,
                    totalValue = total,
                    incomeRatio = ratio
                )
            )
        } catch (exception: Exception) {
            RootResult.Failure(CalculationError)
        }
}