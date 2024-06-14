package com.psychojean.field.impl.amount

import com.psychojean.field.api.amount.AmountValidationUseCase
import com.psychojean.field.api.amount.InvalidAmountException
import com.psychojean.field.api.amount.InvalidAmountType
import java.math.BigInteger
import javax.inject.Inject

internal class DefaultAmountValidationUseCase @Inject constructor() : AmountValidationUseCase {

    override suspend operator fun invoke(value: String): Result<BigInteger> = runCatching {
        if (value.isEmpty()) throw InvalidAmountException(InvalidAmountType.EMPTY)
        if (value.any { it == ',' || it == '.' }) throw InvalidAmountException(InvalidAmountType.CONTAINS_DOT_OR_COMMA)
        val amount = value.toBigIntegerOrNull()
            ?: throw InvalidAmountException(InvalidAmountType.NOT_A_NUMBER)
        if (amount < BigInteger.ONE) throw InvalidAmountException(InvalidAmountType.LESS_THAN_ONE)
        if (amount > BigInteger.valueOf(1_000_000_000)) throw InvalidAmountException(
            InvalidAmountType.MORE_THAN_ONE_BILLION
        )
        amount
    }
}

