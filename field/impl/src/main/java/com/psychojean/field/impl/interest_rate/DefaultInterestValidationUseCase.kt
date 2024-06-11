package com.psychojean.field.impl.interest_rate

import com.psychojean.core.RootResult
import com.psychojean.field.api.interest_rate.InterestRateValidationError
import com.psychojean.field.api.interest_rate.InterestValidationUseCase
import javax.inject.Inject

internal class DefaultInterestValidationUseCase @Inject constructor() :
    InterestValidationUseCase {

    override suspend operator fun invoke(value: String): RootResult<Double, InterestRateValidationError> {
        if (value.isEmpty()) return RootResult.Failure(InterestRateValidationError.EMPTY)
        val interestValue = value.toDoubleOrNull() ?: return RootResult.Failure(
            InterestRateValidationError.NOT_A_NUMBER
        )
        if (interestValue < 0)
            return RootResult.Failure(InterestRateValidationError.LESS_THAN_0)
        if (interestValue > 100)
            return RootResult.Failure(InterestRateValidationError.MORE_THAN_100)
        return RootResult.Success(interestValue)
    }
}
