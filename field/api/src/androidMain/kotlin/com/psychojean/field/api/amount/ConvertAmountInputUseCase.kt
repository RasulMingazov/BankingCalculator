package com.psychojean.field.api.amount

import java.math.BigInteger

interface ConvertAmountInputUseCase {
    suspend operator fun invoke(value: String): Result<BigInteger>
}
