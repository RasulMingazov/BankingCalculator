package com.psychojean.feature.deposit.api.presentation

sealed interface CalculateDepositIntent {

    data class InterestRateChanged(val rate: String) : CalculateDepositIntent

    data class MonthPeriodChanged(val period: String) : CalculateDepositIntent

    data class InitialDepositChanged(val deposit: String) : CalculateDepositIntent
}