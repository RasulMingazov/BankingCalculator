package com.psychojean.field.impl.period_type

import com.psychojean.field.api.period_type.PeriodTypeComponent
import dagger.Binds
import dagger.Module

@Module
internal interface PeriodTypeModule {

    @Binds
    fun bindPeriodTypeFactory(periodTypeComponentFactory: DefaultPeriodTypeComponent.Factory): PeriodTypeComponent.Factory

}
