package com.psychojean.field.impl.period.validation.month

import com.psychojean.core.RootResult
import com.psychojean.field.api.period.validation.month.MonthPeriodValidationError
import com.psychojean.field.api.period.validation.month.MonthPeriodValidationUseCase
import javax.inject.Inject

internal class DefaultMonthPeriodValidationUseCase @Inject constructor(
) : MonthPeriodValidationUseCase {

    override suspend operator fun invoke(month: String): RootResult<Int, MonthPeriodValidationError> {
        if (month.isEmpty()) return RootResult.Failure(MonthPeriodValidationError.EMPTY)
        if (month.any { it == ',' || it == '.' }) return RootResult.Failure(
            MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA
        )
        val monthValue = month.toIntOrNull() ?: return RootResult.Failure(
            MonthPeriodValidationError.NOT_A_NUMBER
        )
        if (monthValue < 1) return RootResult.Failure(
            MonthPeriodValidationError.LESS_THAN_1
        )
        if (monthValue > 120) return RootResult.Failure(
            MonthPeriodValidationError.MORE_THAN_120
        )
        return RootResult.Success(monthValue)
    }
}
