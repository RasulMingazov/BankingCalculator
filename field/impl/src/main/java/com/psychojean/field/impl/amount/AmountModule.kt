package com.psychojean.field.impl.amount

import com.psychojean.field.api.amount.AmountComponent
import com.psychojean.field.api.amount.AmountValidationUseCase
import dagger.Binds
import dagger.Module

@Module
internal interface AmountModule {

    @Binds
    fun bindAmountValidationUseCase(amountValidationUseCase: DefaultAmountValidationUseCase): AmountValidationUseCase

    @Binds
    fun bindAmountFactory(amountComponentFactory: DefaultAmountComponent.Factory): AmountComponent.Factory

}