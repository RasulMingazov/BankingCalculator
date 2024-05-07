package com.psychojean.feature.deposit.api.domain.validation

import com.psychojean.core.Error
import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestRateValidationError
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationError

interface DepositValidationUseCase {

    suspend operator fun invoke(input: DepositInput): RootResult<Unit, DepositValidationError>

}

data class DepositValidationError(
    val amountError: AmountValidationError? = null,
    val interestRateError: InterestRateValidationError? = null,
    val monthPeriodError: MonthPeriodValidationError? = null
) : Error