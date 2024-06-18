package com.psychojean.field.impl.di

import com.psychojean.field.api.amount.AmountComponent
import com.psychojean.field.api.currency_type.CurrencyTypeComponent
import com.psychojean.field.api.di.FieldContainer
import com.psychojean.field.api.interest_rate.InterestRateComponent
import com.psychojean.field.api.period.PeriodComponent
import com.psychojean.field.api.period_type.PeriodTypeComponent
import com.psychojean.field.impl.amount.DefaultAmountComponent
import com.psychojean.field.impl.amount.DefaultConvertAmountInputUseCase
import com.psychojean.field.impl.currency_type.DefaultCurrencyTypeComponent
import com.psychojean.field.impl.interest_rate.DefaultConvertInterestInputUseCase
import com.psychojean.field.impl.interest_rate.DefaultInterestRateComponent
import com.psychojean.field.impl.period.DefaultPeriodComponent
import com.psychojean.field.impl.period.validation.DefaultConvertPeriodInputUseCase
import com.psychojean.field.impl.period.validation.month.DefaultConvertMonthPeriodInputUseCase
import com.psychojean.field.impl.period.validation.year.DefaultConvertYearPeriodInputUseCase
import com.psychojean.field.impl.period_type.DefaultPeriodTypeComponent

class DefaultFieldContainer : FieldContainer {

    override val interestRateComponentFactory: InterestRateComponent.Factory
        get() = DefaultInterestRateComponent.Factory(DefaultConvertInterestInputUseCase())

    override val amountComponentFactory: AmountComponent.Factory
        get() = DefaultAmountComponent.Factory(DefaultConvertAmountInputUseCase())

    override val currencyTypeComponentFactory: CurrencyTypeComponent.Factory
        get() = DefaultCurrencyTypeComponent.Factory()

    override val periodComponentFactory: PeriodComponent.Factory
        get() = DefaultPeriodComponent.Factory(
            DefaultConvertPeriodInputUseCase(
                DefaultConvertYearPeriodInputUseCase(),
                DefaultConvertMonthPeriodInputUseCase(),
            )
        )

    override val periodTypeComponentFactory: PeriodTypeComponent.Factory
        get() = DefaultPeriodTypeComponent.Factory()


}