package com.psychojean.feature.deposit.impl.domain.validation.month

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationError
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationUseCase
import java.math.BigInteger
import javax.inject.Inject

internal class DefaultMonthPeriodValidationUseCase @Inject constructor(
) : MonthPeriodValidationUseCase {

    override suspend operator fun invoke(month: String): RootResult<Unit, MonthPeriodValidationError> {
        if (month.isEmpty()) return RootResult.Failure(MonthPeriodValidationError.EMPTY)
        if (month.any { it == ',' || it == '.' }) return RootResult.Failure(
            MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA
        )
        val monthValue = month.toBigIntegerOrNull() ?: return RootResult.Failure(
            MonthPeriodValidationError.NOT_A_NUMBER
        )
        if (monthValue < BigInteger.ONE) return RootResult.Failure(
            MonthPeriodValidationError.LESS_THAN_1
        )
        if (monthValue > BigInteger.valueOf(120)) return RootResult.Failure(
            MonthPeriodValidationError.MORE_THAN_120
        )
        return RootResult.Success(Unit)
    }
}
