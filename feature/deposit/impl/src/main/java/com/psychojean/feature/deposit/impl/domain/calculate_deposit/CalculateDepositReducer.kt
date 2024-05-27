package com.psychojean.feature.deposit.impl.domain.calculate_deposit

import com.arkivanov.mvikotlin.core.store.Reducer
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore

internal class CalculateDepositReducer : Reducer<CalculateDepositStore.State, CalculateDepositStore.Message> {

    override fun CalculateDepositStore.State.reduce(
        msg: CalculateDepositStore.Message,
    ) = when (msg) {
        is CalculateDepositStore.Message.UpdateInitialDeposit -> copy(
            initialDeposit = msg.deposit
        )
        is CalculateDepositStore.Message.UpdateInitialDepositError -> copy(
            initialDepositError = msg.error
        )
        is CalculateDepositStore.Message.UpdatePeriod -> copy(
            period = msg.period
        )
        is CalculateDepositStore.Message.UpdatePeriodError -> copy(
            periodError = msg.error
        )
        is CalculateDepositStore.Message.UpdateInterestRate -> copy(
            interestRate = msg.rate
        )
        is CalculateDepositStore.Message.UpdateInterestRateError -> copy(
            interestRateError = msg.error
        )
        is CalculateDepositStore.Message.UpdateSelectedCurrencyType -> copy(
            selectedCurrencyType = msg.type
        )
        is CalculateDepositStore.Message.UpdateCurrencyTypes -> copy(
            currencyTypes = msg.types
        )
        is CalculateDepositStore.Message.UpdateCalculationResult -> copy(
            depositAmount = msg.output.depositAmount,
            income = msg.output.income,
            totalValue = msg.output.totalValue,
            incomeRatio = msg.output.incomeRatio,
            )
    }
}