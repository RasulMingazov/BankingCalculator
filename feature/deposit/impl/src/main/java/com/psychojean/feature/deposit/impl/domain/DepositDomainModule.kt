package com.psychojean.feature.deposit.impl.domain

import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationUseCase
import com.psychojean.feature.deposit.impl.domain.validation.amount.DefaultAmountValidationUseCase
import com.psychojean.feature.deposit.impl.domain.validation.interest.DefaultInterestValidationUseCase
import com.psychojean.feature.deposit.impl.domain.validation.month.DefaultMonthPeriodValidationUseCase
import dagger.Binds
import dagger.Module

@Module
internal interface DepositDomainModule {

    @Binds
    fun bindAmountValidationUseCase(amountValidationUseCase: DefaultAmountValidationUseCase): AmountValidationUseCase

    @Binds
    fun bindMonthPeriodValidationUseCase(monthPeriodValidationUseCase: DefaultMonthPeriodValidationUseCase): MonthPeriodValidationUseCase

    @Binds
    fun bindInterestValidationUseCase(interestValidationUseCase: DefaultInterestValidationUseCase): InterestValidationUseCase

}
