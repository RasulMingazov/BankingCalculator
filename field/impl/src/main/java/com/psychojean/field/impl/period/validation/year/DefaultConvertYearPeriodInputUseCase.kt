package com.psychojean.field.impl.period.validation.year

import com.psychojean.field.api.period.validation.year.InvalidYearPeriodException
import com.psychojean.field.api.period.validation.year.InvalidYearType
import com.psychojean.field.api.period.validation.year.ConvertYearPeriodInputUseCase
import java.math.BigInteger

internal class DefaultConvertYearPeriodInputUseCase(
) : ConvertYearPeriodInputUseCase {

    override suspend operator fun invoke(year: String): Result<Int> = runCatching {
        if (year.isEmpty()) throw InvalidYearPeriodException(InvalidYearType.EMPTY)
        if (year.any { it == ',' || it == '.' }) throw InvalidYearPeriodException(InvalidYearType.CONTAINS_DOT_OR_COMMA)
        val yearBigValue = year.toBigIntegerOrNull() ?: throw InvalidYearPeriodException(
            InvalidYearType.NOT_A_NUMBER
        )
        if (yearBigValue < BigInteger.ONE) throw InvalidYearPeriodException(InvalidYearType.LESS_THAN_ONE)
        if (yearBigValue > BigInteger.TEN) throw InvalidYearPeriodException(InvalidYearType.MORE_THAN_TEN)
        yearBigValue.toInt()
    }
}
