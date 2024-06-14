package com.psychojean.field.api.amount

import java.math.BigInteger

interface AmountValidationUseCase {
    suspend operator fun invoke(value: String): Result<BigInteger>
}
