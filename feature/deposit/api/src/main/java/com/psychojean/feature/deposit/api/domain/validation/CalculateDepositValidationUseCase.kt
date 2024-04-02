package com.psychojean.feature.deposit.api.domain.validation

import com.psychojean.core.api.Error
import com.psychojean.core.api.RootResult
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationError

interface CalculateDepositValidationUseCase {

    suspend fun validate(deposit: Deposit): RootResult<Deposit, CalculateDepositError>

}

data class CalculateDepositError(
    val amountError: AmountValidationError? = null,
    val monthPeriodError: MonthPeriodValidationError? = null
) : Error