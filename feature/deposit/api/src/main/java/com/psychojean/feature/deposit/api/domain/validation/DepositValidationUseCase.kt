package com.psychojean.feature.deposit.api.domain.validation

import com.psychojean.core.Error
import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationError
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationError

interface DepositValidationUseCase {

    suspend fun validate(deposit: ValidateDeposit): RootResult<DepositInput, DepositValidationError>

}

data class DepositValidationError(
    val amountError: AmountValidationError? = null,
    val interestRateError: InterestValidationError? = null,
    val monthPeriodError: MonthPeriodValidationError? = null
) : Error