package com.psychojean.feature.deposit.api.presentation

import androidx.compose.runtime.Stable
import com.psychojean.feature.deposit.api.CurrencyType
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

    val monthPeriod: String = "",
    val monthPeriodError: Int? = null,

    val incomeRatio: Float? = null,
    val depositAmount: String = "",
    val income: String = "",
    val totalValue: String = ""
)