package com.psychojean.feature.deposit.api.domain.validation.period

import com.psychojean.core.Error
import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.PeriodType
import com.psychojean.feature.deposit.api.domain.validation.period.month.MonthPeriodValidationError
import com.psychojean.feature.deposit.api.domain.validation.period.year.YearPeriodValidationError

interface PeriodValidationUseCase {

    suspend operator fun invoke(
        period: String,
        periodType: PeriodType
    ): RootResult<Unit, PeriodValidationError>

}

data class PeriodValidationError(
    val monthPeriodValidationError: MonthPeriodValidationError? = null,
    val yearPeriodValidationError: YearPeriodValidationError? = null
): Error