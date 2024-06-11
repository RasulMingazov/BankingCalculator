package com.psychojean.feature.deposit.api.domain

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.mvikotlin.core.store.Store
import com.psychojean.core.CurrencyType
import com.psychojean.core.PeriodType
import java.math.BigDecimal
import java.math.BigInteger

interface CalculateDepositStore :
    Store<CalculateDepositStore.Intent, CalculateDepositStore.State, Nothing>,
    InstanceKeeper.Instance {

    data class State(
        val interestRate: Double,
        val initialDeposit: BigInteger,
        val currencyType: CurrencyType,
        val currencyTypes: List<CurrencyType>,
        val period: Int,
        val periodType: PeriodType,
        val periodTypes: List<PeriodType>,

        val depositAmount: BigDecimal? = null,
        val income: BigDecimal? = null,
        val totalValue: BigDecimal? = null,
        val incomeRatio: Float? = null
    )

    sealed interface Intent {
        sealed interface FieldUpdate : Intent
        data class RateChanged(val rate: Double): FieldUpdate
        data class InitialDepositChanged(val deposit: BigInteger): FieldUpdate
        data class CurrencyTypeChanged(val currencyType: CurrencyType) : FieldUpdate
        data class PeriodTypeChanged(val periodType: PeriodType) : FieldUpdate
        data class PeriodChanged(val period: Int) : FieldUpdate
    }

    sealed interface Message {
        data class UpdateInterestRate(val rate: Double): Message
        data class UpdateCurrencyType(val type: CurrencyType) : Message
        data class  UpdateInitialDeposit(val deposit: BigInteger) : Message
        data class UpdatePeriod(val period: Int) : Message
        data class UpdatePeriodType(val periodType: PeriodType) : Message
        data class UpdateCalculationResult(val output: DepositOutput) : Message
    }

}