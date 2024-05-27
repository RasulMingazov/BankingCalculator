package com.psychojean.feature.deposit.api.presentation

import androidx.compose.runtime.Stable
import com.psychojean.feature.deposit.api.CurrencyType
import com.psychojean.feature.deposit.api.PeriodType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class CalculateDepositUiState(
    val initialDeposit: String = "",
    val initialDepositError: Int? = null,

    val currencyTypes: ImmutableList<CurrencyType> = persistentListOf(),
    val selectedCurrencyName: String = "",

    val interestRate: String = "",
    val interestRateError: Int? = null,

    val period: String = "",
    val periodError: Int? = null,

    val periodTypes: ImmutableList<PeriodType> = persistentListOf(),
    val selectedPeriodName: String = "",

    val incomeRatio: Float? = null,
    val depositAmount: String = "",
    val income: String = "",
    val totalValue: String = ""
)