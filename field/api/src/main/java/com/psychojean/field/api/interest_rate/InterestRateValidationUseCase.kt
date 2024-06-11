package com.psychojean.field.api.interest_rate

import com.psychojean.core.Error
import com.psychojean.core.RootResult

interface InterestValidationUseCase {
    suspend operator fun invoke(value: String): RootResult<Double, InterestRateValidationError>
}

enum class InterestRateValidationError : Error { EMPTY, NOT_A_NUMBER, LESS_THAN_0, MORE_THAN_100 }
