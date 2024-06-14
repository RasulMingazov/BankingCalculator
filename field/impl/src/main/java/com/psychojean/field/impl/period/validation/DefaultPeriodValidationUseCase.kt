package com.psychojean.field.impl.period.validation

import com.psychojean.core.PeriodType
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
    ): Result<Int> = if (periodType == PeriodType.MONTH)
        monthPeriodValidationUseCase(period) else yearPeriodValidationUseCase(period)
}
