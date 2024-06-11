package com.psychojean.field.impl

import com.psychojean.field.impl.amount.AmountModule
import com.psychojean.field.impl.currency_type.CurrencyTypeModule
import com.psychojean.field.impl.interest_rate.InterestRateModule
import com.psychojean.field.impl.period.PeriodModule
import com.psychojean.field.impl.period_type.PeriodTypeModule
import dagger.Module

@Module(includes = [InterestRateModule::class, AmountModule::class, CurrencyTypeModule::class, PeriodModule::class, PeriodTypeModule::class])
class FieldModule