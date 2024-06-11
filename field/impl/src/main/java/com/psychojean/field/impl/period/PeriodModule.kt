package com.psychojean.field.impl.period

import com.psychojean.field.api.period.PeriodComponent
import com.psychojean.field.api.period.validation.PeriodValidationUseCase
import com.psychojean.field.api.period.validation.month.MonthPeriodValidationUseCase
import com.psychojean.field.api.period.validation.year.YearPeriodValidationUseCase
import com.psychojean.field.impl.period.validation.DefaultPeriodValidationUseCase
import com.psychojean.field.impl.period.validation.month.DefaultMonthPeriodValidationUseCase
import com.psychojean.field.impl.period.validation.year.DefaultYearPeriodValidationUseCase
import dagger.Binds
import dagger.Module

@Module
internal interface PeriodModule {

    @Binds
    fun bindMonthValidationUseCase(monthValidationUseCase: DefaultMonthPeriodValidationUseCase): MonthPeriodValidationUseCase

    @Binds
    fun bindYearValidationUseCase(yearValidationUseCase: DefaultYearPeriodValidationUseCase): YearPeriodValidationUseCase

    @Binds
    fun bindPeriodValidationUseCase(periodValidationUseCase: DefaultPeriodValidationUseCase): PeriodValidationUseCase

    @Binds
    fun bindPeriodFactory(periodComponentFactory: DefaultPeriodComponent.Factory): PeriodComponent.Factory

}
