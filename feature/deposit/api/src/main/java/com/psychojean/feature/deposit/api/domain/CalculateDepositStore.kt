package com.psychojean.feature.deposit.api.domain

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.mvikotlin.core.store.Store
import com.psychojean.feature.deposit.api.CurrencyType
import com.psychojean.feature.deposit.api.PeriodType
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestRateValidationError
import com.psychojean.feature.deposit.api.domain.validation.period.PeriodValidationError
import java.math.BigDecimal

interface CalculateDepositStore :
    Store<CalculateDepositStore.Intent, CalculateDepositStore.State, CalculateDepositStore.Label>,
    InstanceKeeper.Instance {

    data class State(
        val initialDeposit: String = "350000",
        val initialDepositError: AmountValidationError? = null,

        val period: String = "9",
        val periodError: PeriodValidationError? = null,

        val interestRate: String = "16",
        val interestRateError: InterestRateValidationError? = null,

        val selectedCurrencyType: CurrencyType = CurrencyType.RUB,
        val currencyTypes: List<CurrencyType> = CurrencyType.entries,

        val selectedPeriodType: PeriodType = PeriodType.MONTH,
        val periodTypes: List<PeriodType> = PeriodType.entries,

        val depositAmount: BigDecimal? = null,
        val income: BigDecimal? = null,
        val totalValue: BigDecimal? = null,
        val incomeRatio: Float? = null
    ) {

        val isAllFieldsCorrect: Boolean
            get() = initialDepositError == null && periodError == null && interestRateError == null
    }

    sealed interface Intent {
        sealed interface FieldUpdate : Intent
        data class InitialDepositChanged(val deposit: String) : FieldUpdate
        data class CurrencyTypeChanged(val currencyType: CurrencyType) : FieldUpdate
        data class PeriodTypeChanged(val periodType: PeriodType) : FieldUpdate
        data class InterestRateChanged(val rate: String) : FieldUpdate
        data class PeriodChanged(val period: String) : FieldUpdate
    }

    sealed interface Message {
        data class UpdateInitialDeposit(val deposit: String) : Message
        data class UpdateInitialDepositError(val error: AmountValidationError?) : Message

        data class UpdatePeriod(val period: String) : Message
        data class UpdatePeriodType(val periodType: PeriodType) : Message
        data class UpdatePeriodError(val error: PeriodValidationError?) : Message

        data class UpdateInterestRate(val rate: String) : Message
        data class UpdateInterestRateError(val error: InterestRateValidationError?) : Message

        data class UpdateSelectedCurrencyType(val type: CurrencyType) : Message

        data class UpdateCalculationResult(val output: DepositOutput) : Message
    }

    sealed interface Label {
    }

}