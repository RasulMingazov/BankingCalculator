package com.psychojean.feature.deposit.impl.domain.validation.period.year

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.period.year.YearPeriodValidationError
import com.psychojean.feature.deposit.api.domain.validation.period.year.YearPeriodValidationUseCase
import java.math.BigInteger
import javax.inject.Inject

internal class DefaultYearPeriodValidationUseCase @Inject constructor(
) : YearPeriodValidationUseCase {

    override suspend operator fun invoke(year: String): RootResult<Unit, YearPeriodValidationError> {
        if (year.isEmpty()) return RootResult.Failure(YearPeriodValidationError.EMPTY)
        if (year.any { it == ',' || it == '.' }) return RootResult.Failure(
            YearPeriodValidationError.CONTAINS_DOT_OR_COMMA
        )
        val monthValue = year.toBigIntegerOrNull() ?: return RootResult.Failure(
            YearPeriodValidationError.NOT_A_NUMBER
        )
        if (monthValue < BigInteger.ONE) return RootResult.Failure(
            YearPeriodValidationError.LESS_THAN_1
        )
        if (monthValue > BigInteger.valueOf(10)) return RootResult.Failure(
            YearPeriodValidationError.MORE_THAN_10
        )
        return RootResult.Success(Unit)
    }
}
