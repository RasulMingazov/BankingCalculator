package com.psychojean.feature.deposit.api.domain.validation.interest

import com.psychojean.core.Error
import com.psychojean.core.RootResult

interface InterestValidationUseCase {

    suspend operator fun invoke(interest: String): RootResult<Unit, InterestRateValidationError>

}


enum class InterestRateValidationError : Error { EMPTY, NOT_A_DIGIT, LESS_THAN_0, MORE_THAN_100 }
