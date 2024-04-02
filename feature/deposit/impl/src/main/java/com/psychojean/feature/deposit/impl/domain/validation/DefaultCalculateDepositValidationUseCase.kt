package com.psychojean.feature.deposit.impl.domain.validation

import com.psychojean.core.api.DispatchersList
import com.psychojean.core.api.RootResult
import com.psychojean.feature.deposit.api.domain.validation.CalculateDepositError
import com.psychojean.feature.deposit.api.domain.validation.CalculateDepositValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.Deposit
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCalculateDepositValidationUseCase @Inject constructor(
    private val dispatchersList: DispatchersList,
    private val amountValidationUseCase: AmountValidationUseCase,
    private val monthPeriodValidationUseCase: MonthPeriodValidationUseCase
) : CalculateDepositValidationUseCase {

    override suspend fun validate(deposit: Deposit): RootResult<Deposit, CalculateDepositError> =
        withContext(dispatchersList.io()) {
            val amountValidate = amountValidationUseCase.validate(deposit.amount)
            val monthPeriodValidate = monthPeriodValidationUseCase.validate(deposit.monthPeriod)

            val isFailed = listOf(amountValidate, monthPeriodValidate).any { it.isError }

            if (isFailed) RootResult.Failure(
                CalculateDepositError(
                    amountError = amountValidate.errorOrNull,
                    monthPeriodError = monthPeriodValidate.errorOrNull
                )
            ) else RootResult.Success(deposit)
        }
}