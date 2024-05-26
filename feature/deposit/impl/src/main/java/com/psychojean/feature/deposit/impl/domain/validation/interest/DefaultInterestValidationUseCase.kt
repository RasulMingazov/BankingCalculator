package com.psychojean.feature.deposit.impl.domain.validation.interest

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestRateValidationError
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationUseCase
import java.math.BigDecimal
import javax.inject.Inject

internal class DefaultInterestValidationUseCase @Inject constructor() : InterestValidationUseCase {

    override suspend operator fun invoke(interest: String): RootResult<Unit, InterestRateValidationError> {
        if (interest.isEmpty()) return RootResult.Failure(InterestRateValidationError.EMPTY)
        val interestValue = interest.toBigDecimalOrNull() ?: return RootResult.Failure(
            InterestRateValidationError.NOT_A_NUMBER
        )
        if (interestValue < BigDecimal.ZERO)
            return RootResult.Failure(InterestRateValidationError.LESS_THAN_0)
        if (interestValue > BigDecimal.valueOf(100))
            return RootResult.Failure(InterestRateValidationError.MORE_THAN_100)
        return RootResult.Success(Unit)
    }
}
