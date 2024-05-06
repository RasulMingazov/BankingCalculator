package com.psychojean.feature.deposit.impl.domain.validation.interest

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationError
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationUseCase
import java.math.BigDecimal
import javax.inject.Inject

internal class DefaultInterestValidationUseCase @Inject constructor() : InterestValidationUseCase {

    override suspend operator fun invoke(interest: String): RootResult<Double, InterestValidationError> {
        if (interest.isEmpty()) return RootResult.Failure(InterestValidationError.EMPTY)
        val interestValue = interest.toBigDecimalOrNull() ?: return RootResult.Failure(
            InterestValidationError.INCORRECT
        )
        if (interestValue < BigDecimal.ZERO)
            return RootResult.Failure(InterestValidationError.LESS_THAN_0)
        if (interestValue > BigDecimal.valueOf(100))
            return RootResult.Failure(InterestValidationError.MORE_THAN_100)
        return RootResult.Success(interestValue.toDouble())
    }
}
