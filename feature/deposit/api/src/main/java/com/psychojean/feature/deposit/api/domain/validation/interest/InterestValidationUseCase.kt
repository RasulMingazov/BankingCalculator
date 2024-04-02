package com.psychojean.feature.deposit.api.domain.validation.interest

import com.psychojean.core.api.Error
import com.psychojean.core.api.RootResult

interface InterestValidationUseCase {

    suspend fun validate(interest: String): RootResult<Double, InterestValidationError>

}

enum class InterestValidationError : Error { EMPTY, INCORRECT, LESS_THAN_0, MORE_THAN_100 }
