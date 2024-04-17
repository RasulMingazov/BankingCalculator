package com.psychojean.feature.deposit.impl.domain.validation.month

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationError
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationUseCase
import java.math.BigInteger
import javax.inject.Inject

internal class DefaultMonthPeriodValidationUseCase @Inject constructor(
) : MonthPeriodValidationUseCase {

    override suspend fun validate(month: String): RootResult<Int, MonthPeriodValidationError> {
        if (month.isEmpty()) return RootResult.Failure(MonthPeriodValidationError.EMPTY)
        if (month.contains('.') || month.contains(',')) return RootResult.Failure(
            MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA
        )
        val monthValue = month.toBigIntegerOrNull() ?: return RootResult.Failure(
            MonthPeriodValidationError.INCORRECT
        )
        if (monthValue < BigInteger.ONE) return RootResult.Failure(
            MonthPeriodValidationError.LESS_THAN_1
        )
        if (monthValue > BigInteger.valueOf(120)) return RootResult.Failure(
            MonthPeriodValidationError.MORE_THAN_60
        )
        return RootResult.Success(monthValue.toInt())
    }
}
