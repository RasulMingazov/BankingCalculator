package com.psychojean.field.impl.period.validation

import com.psychojean.core.PeriodType
import com.psychojean.field.api.period.validation.ConvertPeriodInputUseCase
import com.psychojean.field.api.period.validation.month.ConvertMonthPeriodInputUseCase
import com.psychojean.field.api.period.validation.year.ConvertYearPeriodInputUseCase

internal class DefaultConvertPeriodInputUseCase(
    private val convertYearPeriodInputUseCase: ConvertYearPeriodInputUseCase,
    private val convertMonthPeriodInputUseCase: ConvertMonthPeriodInputUseCase
) : ConvertPeriodInputUseCase {

    override suspend operator fun invoke(
        period: String,
        periodType: PeriodType
    ): Result<Int> = if (periodType == PeriodType.MONTH)
        convertMonthPeriodInputUseCase(period) else convertYearPeriodInputUseCase(period)
}
