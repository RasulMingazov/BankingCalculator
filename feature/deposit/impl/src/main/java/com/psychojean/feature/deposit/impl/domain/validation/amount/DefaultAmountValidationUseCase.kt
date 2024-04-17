package com.psychojean.feature.deposit.impl.domain.validation.amount

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationUseCase
import java.math.BigDecimal
import javax.inject.Inject

internal class DefaultAmountValidationUseCase @Inject constructor() : AmountValidationUseCase {

    override suspend fun validate(amount: String): RootResult<BigDecimal, AmountValidationError> {
        if (amount.isEmpty()) return RootResult.Failure(AmountValidationError.EMPTY)
        val amountValue = amount.toBigDecimalOrNull() ?: return RootResult.Failure(
            AmountValidationError.INCORRECT
        )
        if (amountValue < BigDecimal.ONE)
            return RootResult.Failure(AmountValidationError.LESS_THAN_1)
        return RootResult.Success(amountValue)
    }
}
