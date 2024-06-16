package com.psychojean.field.impl.amount

import com.psychojean.field.api.amount.AmountComponent
import com.psychojean.field.api.amount.ConvertAmountInputUseCase
import dagger.Binds
import dagger.Module

@Module
internal interface AmountModule {

    @Binds
    fun bindAmountValidationUseCase(amountValidationUseCase: DefaultConvertAmountInputUseCase): ConvertAmountInputUseCase

    @Binds
    fun bindAmountFactory(amountComponentFactory: DefaultAmountComponent.Factory): AmountComponent.Factory

}