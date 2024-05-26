package com.psychojean.feature.deposit.api.presentation

import androidx.compose.runtime.Stable
import com.psychojean.feature.deposit.api.CurrencyType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
data class CalculateDepositUiState(
    val initialDeposit: String = "350000",
    val initialDepositError: Int? = null,

    val interestRate: String = "4.3",
    val interestRateError: Int? = null,

    val monthPeriod: String = "9",
    val monthPeriodError: Int? = null,

    val currencyTypes: ImmutableList<CurrencyType> = CurrencyType.entries.toImmutableList(),
    val selectedCurrencyType: CurrencyType = currencyTypes.first(),

    val income: String = "",
)
