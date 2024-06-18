package com.psychojean.field.impl.interest_rate

import com.psychojean.field.api.interest_rate.ConvertInterestInputUseCase
import com.psychojean.field.api.interest_rate.InvalidInterestRateException
import com.psychojean.field.api.interest_rate.InvalidInterestRateType

internal class DefaultConvertInterestInputUseCase :
    ConvertInterestInputUseCase {

    override suspend operator fun invoke(value: String): Result<Double> = runCatching {
        if (value.isEmpty()) throw InvalidInterestRateException(InvalidInterestRateType.EMPTY)
        if (value.contains(' ')) throw InvalidInterestRateException(InvalidInterestRateType.NOT_A_NUMBER)
        val interestValue = value.toDoubleOrNull() ?: throw InvalidInterestRateException(
            InvalidInterestRateType.NOT_A_NUMBER
        )
        if (interestValue < 0) throw InvalidInterestRateException(InvalidInterestRateType.LESS_THAN_ZERO)
        if (interestValue > 100) throw InvalidInterestRateException(InvalidInterestRateType.MORE_THAN_ONE_HUNDRED)
        interestValue
    }
}
