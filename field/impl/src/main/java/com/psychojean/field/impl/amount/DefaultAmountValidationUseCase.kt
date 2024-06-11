package com.psychojean.field.impl.amount

import com.psychojean.core.RootResult
import com.psychojean.field.api.amount.AmountValidationError
import com.psychojean.field.api.amount.AmountValidationUseCase
import java.math.BigInteger
import javax.inject.Inject

internal class DefaultAmountValidationUseCase @Inject constructor() :
    AmountValidationUseCase {

    override suspend operator fun invoke(value: String): RootResult<BigInteger, AmountValidationError> {
        if (value.isEmpty()) return RootResult.Failure(AmountValidationError.EMPTY)
        if (value.any { it == ',' || it == '.' }) return RootResult.Failure(AmountValidationError.CONTAINS_DOT_OR_COMMA)
        val amount = value.toBigIntegerOrNull()
            ?: return RootResult.Failure(AmountValidationError.NOT_A_NUMBER)
        if (amount < BigInteger.ONE)
            return RootResult.Failure(AmountValidationError.LESS_THAN_1)
        if (amount > BigInteger.valueOf(1_000_000_000)) return RootResult.Failure(AmountValidationError.MORE_THAN_1_BILLION)
        return RootResult.Success(amount)
    }
}

