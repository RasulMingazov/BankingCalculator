package com.psychojean.field.api.period.validation

import com.psychojean.core.PeriodType

interface PeriodValidationUseCase {

    suspend operator fun invoke(
        period: String,
        periodType: PeriodType
    ): Result<Int>

}
