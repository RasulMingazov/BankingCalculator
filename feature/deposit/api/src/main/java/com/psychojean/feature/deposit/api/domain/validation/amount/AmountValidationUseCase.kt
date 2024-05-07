package com.psychojean.feature.deposit.api.domain.validation.amount

import com.psychojean.core.Error
import com.psychojean.core.RootResult

interface AmountValidationUseCase {

    suspend operator fun invoke(amount: String): RootResult<Unit, AmountValidationError>

}

enum class AmountValidationError : Error { EMPTY, NOT_A_DIGIT, LESS_THAN_1, MORE_THAN_1_BILLION, CONTAINS_DOT_OR_COMMA }
