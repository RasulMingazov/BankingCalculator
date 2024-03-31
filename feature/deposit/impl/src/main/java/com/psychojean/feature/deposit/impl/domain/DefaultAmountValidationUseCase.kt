package com.psychojean.feature.deposit.impl.domain

import com.psychojean.core.api.RootResult
import com.psychojean.feature.deposit.api.domain.AmountValidationError
import com.psychojean.feature.deposit.api.domain.AmountValidationUseCase
import java.math.BigDecimal
import javax.inject.Inject

internal class DefaultAmountValidationUseCase @Inject constructor(): AmountValidationUseCase {

    override fun validate(amount: String): RootResult<Unit, AmountValidationError> {
        if (amount.isBlank()) return RootResult.Failure(AmountValidationError.EMPTY)
        val amountValue = try {
            amount.toBigDecimal()
        } catch (e: NumberFormatException) {
            return RootResult.Failure(AmountValidationError.NOT_DIGIT)
        }
        if (amountValue < BigDecimal.ONE)
            return RootResult.Failure(AmountValidationError.LESS_THAN_ONE)
        return RootResult.Success(Unit)
    }
}