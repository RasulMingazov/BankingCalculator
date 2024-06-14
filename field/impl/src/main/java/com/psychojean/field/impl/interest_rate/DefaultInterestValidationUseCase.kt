package com.psychojean.field.impl.interest_rate

import com.psychojean.field.api.interest_rate.InterestValidationUseCase
import com.psychojean.field.api.interest_rate.InvalidInterestRateException
import com.psychojean.field.api.interest_rate.InvalidInterestRateType
import javax.inject.Inject

internal class DefaultInterestValidationUseCase @Inject constructor() :
    InterestValidationUseCase {

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
