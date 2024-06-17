package com.psychojean.feature.deposit.impl.domain

import com.psychojean.core.PeriodType
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.domain.DepositOutput

internal class DefaultCalculateDepositUseCase : CalculateDepositUseCase {

    override suspend fun invoke(depositInput: DepositInput): Result<DepositOutput> = runCatching {
        val interestRate = depositInput.interestRate / 100
        val period =
            if (depositInput.periodType == PeriodType.MONTH) depositInput.period.toDouble() / 12.0 else depositInput.period.toDouble()
        val initial = depositInput.initialDeposit
        val income =
            initial.toBigDecimal() * interestRate.toBigDecimal() * period.toBigDecimal()
        val total = income + initial.toBigDecimal()
        val ratio = (income / total).toFloat()
        DepositOutput(
            depositAmount = initial.toBigDecimal(),
            income = income,
            totalValue = total,
            incomeRatio = ratio
        )
    }
}