package com.psychojean.feature.deposit.api.domain.validation.amount

import com.psychojean.core.Error
import com.psychojean.core.RootResult
import java.math.BigDecimal

interface AmountValidationUseCase {

    suspend fun validate(amount: String): RootResult<BigDecimal, AmountValidationError>

}

enum class AmountValidationError : Error { EMPTY, INCORRECT, LESS_THAN_1 }
