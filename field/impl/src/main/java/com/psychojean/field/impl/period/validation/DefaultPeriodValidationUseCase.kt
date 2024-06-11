package com.psychojean.field.impl.period.validation

import com.psychojean.core.PeriodType
import com.psychojean.core.RootResult
import com.psychojean.field.api.period.validation.PeriodValidationError
import com.psychojean.field.api.period.validation.PeriodValidationUseCase
import com.psychojean.field.api.period.validation.month.MonthPeriodValidationUseCase
import com.psychojean.field.api.period.validation.year.YearPeriodValidationUseCase
import javax.inject.Inject

internal class DefaultPeriodValidationUseCase @Inject constructor(
    private val yearPeriodValidationUseCase: YearPeriodValidationUseCase,
    private val monthPeriodValidationUseCase: MonthPeriodValidationUseCase
) : PeriodValidationUseCase {

    override suspend operator fun invoke(
        period: String,
        periodType: PeriodType
    ): RootResult<Int, PeriodValidationError> = if (periodType == PeriodType.MONTH) {
        val monthValidateResult = monthPeriodValidationUseCase(period)
        if (monthValidateResult.isSuccess) RootResult.Success(monthValidateResult.dataOrThrow) else
            RootResult.Failure(PeriodValidationError(monthPeriodValidationError = monthValidateResult.errorOrThrow))
    } else {
        val yearValidateResult = yearPeriodValidationUseCase(period)
        if (yearValidateResult.isSuccess) RootResult.Success(yearValidateResult.dataOrThrow) else
            RootResult.Failure(PeriodValidationError(yearPeriodValidationError = yearValidateResult.errorOrThrow))
    }
}
