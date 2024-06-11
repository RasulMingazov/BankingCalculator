package com.psychojean.feature.deposit.impl.domain.calculate_deposit

import com.arkivanov.mvikotlin.core.store.Reducer
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore

internal class CalculateDepositReducer :
    Reducer<CalculateDepositStore.State, CalculateDepositStore.Message> {

    override fun CalculateDepositStore.State.reduce(
        msg: CalculateDepositStore.Message,
    ) = when (msg) {
        is CalculateDepositStore.Message.UpdateInterestRate -> copy(interestRate = msg.rate)
        is CalculateDepositStore.Message.UpdateCurrencyType -> copy(currencyType = msg.type)
        is CalculateDepositStore.Message.UpdateInitialDeposit -> copy(initialDeposit = msg.deposit)
        is CalculateDepositStore.Message.UpdatePeriodType -> copy(periodType = msg.periodType)
        is CalculateDepositStore.Message.UpdatePeriod -> copy(period = msg.period)
        is CalculateDepositStore.Message.UpdateCalculationResult -> copy(
            depositAmount = msg.output.depositAmount,
            income = msg.output.income,
            totalValue = msg.output.totalValue,
            incomeRatio = msg.output.incomeRatio,
        )
    }
}