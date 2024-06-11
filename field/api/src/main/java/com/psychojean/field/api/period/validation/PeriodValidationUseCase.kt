package com.psychojean.field.api.period.validation

import com.psychojean.core.Error
import com.psychojean.core.PeriodType
import com.psychojean.core.RootResult
import com.psychojean.field.api.period.validation.month.MonthPeriodValidationError
import com.psychojean.field.api.period.validation.year.YearPeriodValidationError

interface PeriodValidationUseCase {

    suspend operator fun invoke(
        period: String,
        periodType: PeriodType
    ): RootResult<Int, PeriodValidationError>

}

data class PeriodValidationError(
    val monthPeriodValidationError: MonthPeriodValidationError? = null,
    val yearPeriodValidationError: YearPeriodValidationError? = null
): Error