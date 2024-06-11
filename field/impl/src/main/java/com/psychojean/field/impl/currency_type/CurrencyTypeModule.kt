package com.psychojean.field.impl.currency_type

import com.psychojean.field.api.currency_type.CurrencyTypeComponent
import dagger.Binds
import dagger.Module

@Module
internal interface CurrencyTypeModule {

    @Binds
    fun bindCurrencyTypeFactory(currencyTypeComponentFactory: DefaultCurrencyTypeComponent.Factory): CurrencyTypeComponent.Factory

}
