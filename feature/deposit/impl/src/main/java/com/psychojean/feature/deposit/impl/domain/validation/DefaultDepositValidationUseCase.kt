package com.psychojean.feature.deposit.impl.domain.validation

import com.psychojean.core.DispatchersList
import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.DepositValidationError
import com.psychojean.feature.deposit.api.domain.validation.DepositValidationUseCase
import com.psychojean.feature.deposit.api.domain.DepositInput
import com.psychojean.feature.deposit.api.domain.validation.ValidateDeposit
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultDepositValidationUseCase @Inject constructor(
    private val dispatchersList: DispatchersList,
    private val amountValidationUseCase: AmountValidationUseCase,
    private val interestValidationUseCase: InterestValidationUseCase,
    private val monthPeriodValidationUseCase: MonthPeriodValidationUseCase
) : DepositValidationUseCase {

    override suspend operator fun invoke(deposit: ValidateDeposit): RootResult<DepositInput, DepositValidationError> =
        withContext(dispatchersList.io()) {
            val amountValidate = amountValidationUseCase(deposit.amount)
            val interestValidate = interestValidationUseCase(deposit.interest)
            val monthPeriodValidate = monthPeriodValidationUseCase(deposit.monthPeriod)

            val isFailed =
                listOf(amountValidate, interestValidate, monthPeriodValidate).any { it.isError }

            if (isFailed) RootResult.Failure(
                DepositValidationError(
                    amountError = amountValidate.errorOrNull,
                    interestRateError = interestValidate.errorOrNull,
                    monthPeriodError = monthPeriodValidate.errorOrNull
                )
            ) else RootResult.Success(
                DepositInput(
                    initialDeposit = amountValidate.dataOrThrow,
                    interest = interestValidate.dataOrThrow,
                    monthPeriod = monthPeriodValidate.dataOrThrow
                )
            )
        }
}