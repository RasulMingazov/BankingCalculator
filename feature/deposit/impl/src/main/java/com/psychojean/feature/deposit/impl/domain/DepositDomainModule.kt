package com.psychojean.feature.deposit.impl.domain

import com.psychojean.feature.deposit.api.domain.AmountValidationUseCase
import dagger.Binds
import dagger.Module

@Module
internal interface DepositDomainModule {

    @Binds
    fun bindAmountValidationUseCase(amountValidationUseCase: DefaultAmountValidationUseCase): AmountValidationUseCase
}
