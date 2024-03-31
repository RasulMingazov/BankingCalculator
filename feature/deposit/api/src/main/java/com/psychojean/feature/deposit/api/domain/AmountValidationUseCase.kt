package com.psychojean.feature.deposit.api.domain

import com.psychojean.core.api.Error
import com.psychojean.core.api.RootResult

interface AmountValidationUseCase {

    fun validate(amount: String): RootResult<Unit, AmountValidationError>

}

enum class AmountValidationError : Error { EMPTY, NOT_DIGIT, LESS_THAN_ONE }
