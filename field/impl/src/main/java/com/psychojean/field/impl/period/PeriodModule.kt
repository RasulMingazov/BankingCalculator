package com.psychojean.field.impl.period

import com.psychojean.field.api.period.PeriodComponent
import com.psychojean.field.api.period.validation.ConvertPeriodInputUseCase
import com.psychojean.field.api.period.validation.month.ConvertMonthPeriodInputUseCase
import com.psychojean.field.api.period.validation.year.ConvertYearPeriodInputUseCase
import com.psychojean.field.impl.period.validation.DefaultConvertPeriodInputUseCase
import com.psychojean.field.impl.period.validation.month.DefaultConvertMonthPeriodInputUseCase
import com.psychojean.field.impl.period.validation.year.DefaultConvertYearPeriodInputUseCase
import dagger.Binds
import dagger.Module

@Module
internal interface PeriodModule {

    @Binds
    fun bindMonthValidationUseCase(monthValidationUseCase: DefaultConvertMonthPeriodInputUseCase): ConvertMonthPeriodInputUseCase

    @Binds
    fun bindYearValidationUseCase(yearValidationUseCase: DefaultConvertYearPeriodInputUseCase): ConvertYearPeriodInputUseCase

    @Binds
    fun bindPeriodValidationUseCase(periodValidationUseCase: DefaultConvertPeriodInputUseCase): ConvertPeriodInputUseCase

    @Binds
    fun bindPeriodFactory(periodComponentFactory: DefaultPeriodComponent.Factory): PeriodComponent.Factory

}
