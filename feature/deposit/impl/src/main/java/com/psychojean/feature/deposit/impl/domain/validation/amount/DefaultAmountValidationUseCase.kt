package com.psychojean.feature.deposit.impl.domain.validation.amount

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationUseCase
import java.math.BigInteger
import javax.inject.Inject

internal class DefaultAmountValidationUseCase @Inject constructor() : AmountValidationUseCase {

    override suspend operator fun invoke(amount: String): RootResult<Unit, AmountValidationError> {
        if (amount.isEmpty()) return RootResult.Failure(AmountValidationError.EMPTY)
        if (amount.any { it == ',' || it == '.' }) return RootResult.Failure(AmountValidationError.CONTAINS_DOT_OR_COMMA)
        val value = amount.toBigIntegerOrNull()
            ?: return RootResult.Failure(AmountValidationError.NOT_A_NUMBER)
        if (value < BigInteger.ONE)
            return RootResult.Failure(AmountValidationError.LESS_THAN_1)
        if (value > BigInteger.valueOf(1_000_000_000)) return RootResult.Failure(AmountValidationError.MORE_THAN_1_BILLION)
        return RootResult.Success(Unit)
    }
}

