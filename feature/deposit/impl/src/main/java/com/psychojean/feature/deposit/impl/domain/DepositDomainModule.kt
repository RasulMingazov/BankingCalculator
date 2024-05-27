package com.psychojean.feature.deposit.impl.domain

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.psychojean.feature.deposit.api.CurrencyType
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationUseCase
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationUseCase
import com.psychojean.feature.deposit.impl.domain.calculate_deposit.CalculateDepositExecutor
import com.psychojean.feature.deposit.impl.domain.calculate_deposit.CalculateDepositReducer
import com.psychojean.feature.deposit.impl.domain.validation.amount.DefaultAmountValidationUseCase
import com.psychojean.feature.deposit.impl.domain.validation.interest.DefaultInterestValidationUseCase
import com.psychojean.feature.deposit.impl.domain.validation.month.DefaultMonthPeriodValidationUseCase
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

    companion object {

        @Provides
        fun provideCalculateDepositStore(calculateDepositUseCase: CalculateDepositUseCase, amountValidationUseCase: AmountValidationUseCase, monthPeriodValidationUseCase: MonthPeriodValidationUseCase, interestRateValidationUseCase: InterestValidationUseCase): CalculateDepositStore =
            object : CalculateDepositStore,
                Store<CalculateDepositStore.Intent, CalculateDepositStore.State, CalculateDepositStore.Label> by DefaultStoreFactory().create(
                    name = CalculateDepositStore::class.simpleName,
                    initialState = CalculateDepositStore.State(),
                    bootstrapper = SimpleBootstrapper(CalculateDepositExecutor.Action.InitialCalculate),
                    executorFactory = { CalculateDepositExecutor(calculateDepositUseCase, amountValidationUseCase, monthPeriodValidationUseCase, interestRateValidationUseCase) },
                    reducer = CalculateDepositReducer()
                ) {}
    }
}
