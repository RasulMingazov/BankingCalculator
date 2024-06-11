package com.psychojean.field.impl.period.validation.year

import com.psychojean.core.RootResult
import com.psychojean.field.api.period.validation.year.YearPeriodValidationError
import com.psychojean.field.api.period.validation.year.YearPeriodValidationUseCase
import javax.inject.Inject

internal class DefaultYearPeriodValidationUseCase @Inject constructor(
) : YearPeriodValidationUseCase {

    override suspend operator fun invoke(year: String): RootResult<Int, YearPeriodValidationError> {
        if (year.isEmpty()) return RootResult.Failure(YearPeriodValidationError.EMPTY)
        if (year.any { it == ',' || it == '.' }) return RootResult.Failure(
            YearPeriodValidationError.CONTAINS_DOT_OR_COMMA
        )
        val monthValue = year.toIntOrNull() ?: return RootResult.Failure(
            YearPeriodValidationError.NOT_A_NUMBER
        )
        if (monthValue < 1) return RootResult.Failure(
            YearPeriodValidationError.LESS_THAN_1
        )
        if (monthValue > 10) return RootResult.Failure(
            YearPeriodValidationError.MORE_THAN_10
        )
        return RootResult.Success(monthValue)
    }
}
