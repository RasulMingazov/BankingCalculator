package com.psychojean.feature.deposit.impl.domain

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.period.PeriodValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.period.month.MonthPeriodValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.period.year.YearPeriodValidationUseCase
import com.psychojean.feature.deposit.impl.domain.calculate_deposit.CalculateDepositExecutor
import com.psychojean.feature.deposit.impl.domain.calculate_deposit.CalculateDepositReducer
import com.psychojean.feature.deposit.impl.domain.validation.amount.DefaultAmountValidationUseCase
import com.psychojean.feature.deposit.impl.domain.validation.interest.DefaultInterestValidationUseCase
import com.psychojean.feature.deposit.impl.domain.validation.period.DefaultPeriodValidationUseCase
import com.psychojean.feature.deposit.impl.domain.validation.period.month.DefaultMonthPeriodValidationUseCase
import com.psychojean.feature.deposit.impl.domain.validation.period.year.DefaultYearPeriodValidationUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface DepositDomainModule {

    @Binds
    fun bindAmountValidationUseCase(amountValidationUseCase: DefaultAmountValidationUseCase): AmountValidationUseCase

    @Binds
    fun bindMonthPeriodValidationUseCase(monthPeriodValidationUseCase: DefaultMonthPeriodValidationUseCase): MonthPeriodValidationUseCase

    @Binds
    fun bindInterestValidationUseCase(interestValidationUseCase: DefaultInterestValidationUseCase): InterestValidationUseCase

    @Binds
    fun bindCalculateDepositUseCase(calculateDepositUseCase: DefaultCalculateDepositUseCase): CalculateDepositUseCase

    @Binds
    fun bindYearValidationUseCase(yearValidationUseCase: DefaultYearPeriodValidationUseCase): YearPeriodValidationUseCase

    @Binds
    fun bindPeriodValidationUseCase(periodValidationUseCase: DefaultPeriodValidationUseCase): PeriodValidationUseCase

    companion object {

        @Provides
        fun provideCalculateDepositStore(
            calculateDepositUseCase: CalculateDepositUseCase,
            amountValidationUseCase: AmountValidationUseCase,
            periodValidationUseCase: PeriodValidationUseCase,
            interestRateValidationUseCase: InterestValidationUseCase
        ): CalculateDepositStore =
            object : CalculateDepositStore,
                Store<CalculateDepositStore.Intent, CalculateDepositStore.State, CalculateDepositStore.Label> by DefaultStoreFactory().create(
                    name = CalculateDepositStore::class.simpleName,
                    initialState = CalculateDepositStore.State(),
                    bootstrapper = SimpleBootstrapper(CalculateDepositExecutor.Action.InitialCalculate),
                    executorFactory = {
                        CalculateDepositExecutor(
                            calculateDepositUseCase,
                            amountValidationUseCase,
                            interestRateValidationUseCase,
                            periodValidationUseCase
                        )
                    },
                    reducer = CalculateDepositReducer()
                ) {}
    }
}
