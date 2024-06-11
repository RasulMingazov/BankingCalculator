package com.psychojean.feature.deposit.impl.domain

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.psychojean.core.CurrencyType
import com.psychojean.core.PeriodType
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.domain.CalculateDepositUseCase
import com.psychojean.feature.deposit.impl.domain.calculate_deposit.CalculateDepositExecutor
import com.psychojean.feature.deposit.impl.domain.calculate_deposit.CalculateDepositReducer
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.math.BigInteger

@Module
internal interface DepositDomainModule {

    @Binds
    fun bindCalculateDepositUseCase(calculateDepositUseCase: DefaultCalculateDepositUseCase): CalculateDepositUseCase

    companion object {

        @Provides
        fun provideCalculateDepositStore(
            calculateDepositUseCase: CalculateDepositUseCase
        ): CalculateDepositStore =
            object : CalculateDepositStore,
                Store<CalculateDepositStore.Intent, CalculateDepositStore.State, Nothing> by DefaultStoreFactory().create(
                    name = CalculateDepositStore::class.simpleName,
                    initialState = CalculateDepositStore.State(
                        interestRate = 12.0,
                        currencyType = CurrencyType.RUB,
                        currencyTypes = CurrencyType.entries,
                        periodTypes = PeriodType.entries,
                        initialDeposit = BigInteger("300000"),
                        period = 9,
                        periodType = PeriodType.MONTH
                    ),
                    bootstrapper = SimpleBootstrapper(CalculateDepositExecutor.Action.InitialCalculate),
                    executorFactory = {
                        CalculateDepositExecutor(calculateDepositUseCase)
                    },
                    reducer = CalculateDepositReducer()
                ) {}
    }
}
