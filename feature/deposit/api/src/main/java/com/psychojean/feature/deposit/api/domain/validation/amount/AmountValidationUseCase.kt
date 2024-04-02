package com.psychojean.feature.deposit.api.domain.validation.amount

import com.psychojean.core.api.Error
import com.psychojean.core.api.RootResult
import java.math.BigDecimal

interface AmountValidationUseCase {

    suspend fun validate(amount: String): RootResult<BigDecimal, AmountValidationError>

}

enum class AmountValidationError : Error { EMPTY, INCORRECT, LESS_THAN_1 }
