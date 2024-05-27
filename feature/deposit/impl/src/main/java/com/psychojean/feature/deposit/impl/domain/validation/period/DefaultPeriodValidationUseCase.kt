package com.psychojean.feature.deposit.impl.domain.validation.period

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.PeriodType
import com.psychojean.feature.deposit.api.domain.validation.period.PeriodValidationError
import com.psychojean.feature.deposit.api.domain.validation.period.PeriodValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.period.month.MonthPeriodValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.period.year.YearPeriodValidationUseCase
import javax.inject.Inject

internal class DefaultPeriodValidationUseCase @Inject constructor(
    private val yearPeriodValidationUseCase: YearPeriodValidationUseCase,
    private val monthPeriodValidationUseCase: MonthPeriodValidationUseCase
) : PeriodValidationUseCase {

    override suspend operator fun invoke(
        period: String,
        periodType: PeriodType
    ): RootResult<Unit, PeriodValidationError> = if (periodType == PeriodType.MONTH) {
        val validate = monthPeriodValidationUseCase(period)
        if (validate.isSuccess) RootResult.Success(Unit) else RootResult.Failure(
            PeriodValidationError(monthPeriodValidationError = validate.errorOrNull)
        )
    } else {
        val validate = yearPeriodValidationUseCase(period)
        if (validate.isSuccess) RootResult.Success(Unit) else RootResult.Failure(
            PeriodValidationError(yearPeriodValidationError = validate.errorOrNull)
        )
    }
}
