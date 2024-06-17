package com.psychojean.field.api.di

import com.psychojean.field.api.amount.AmountComponent
import com.psychojean.field.api.currency_type.CurrencyTypeComponent
import com.psychojean.field.api.interest_rate.InterestRateComponent
import com.psychojean.field.api.period.PeriodComponent
import com.psychojean.field.api.period_type.PeriodTypeComponent

interface FieldContainer {
    val interestRateComponentFactory: InterestRateComponent.Factory
    val amountComponentFactory: AmountComponent.Factory
    val currencyTypeComponentFactory: CurrencyTypeComponent.Factory
    val periodComponentFactory: PeriodComponent.Factory
    val periodTypeComponentFactory: PeriodTypeComponent.Factory

}