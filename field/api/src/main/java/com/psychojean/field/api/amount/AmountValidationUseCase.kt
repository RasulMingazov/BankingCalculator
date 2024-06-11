package com.psychojean.field.api.amount

import com.psychojean.core.Error
import com.psychojean.core.RootResult
import java.math.BigInteger

interface AmountValidationUseCase {
    suspend operator fun invoke(value: String): RootResult<BigInteger, AmountValidationError>
}

enum class AmountValidationError :
    Error { EMPTY, NOT_A_NUMBER, LESS_THAN_1, MORE_THAN_1_BILLION, CONTAINS_DOT_OR_COMMA }
