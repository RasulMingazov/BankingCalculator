package com.psychojean.feature.deposit.api.presentation

import com.psychojean.feature.deposit.api.CurrencyType

sealed interface CalculateDepositIntent {

    sealed interface FieldUpdate : CalculateDepositIntent

    data class InterestRateChanged(val rate: String) : FieldUpdate

    data class MonthPeriodChanged(val period: String) : FieldUpdate

    data class InitialDepositChanged(val deposit: String) : FieldUpdate

    data class CurrencyTypeChanged(val currencyType: CurrencyType) : FieldUpdate
}