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
            val income =
                depositInput.initialDeposit.toBigDecimal() * interestRate.toBigDecimal() * period.toBigDecimal()
            RootResult.Success(
                DepositOutput(
                    income = income,
                    endAmount = income + depositInput.initialDeposit.toBigDecimal(),
                )
            )
        } catch (exception: Exception) {
            RootResult.Failure(CalculationError)
        }
}