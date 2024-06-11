package com.psychojean.feature.deposit.api.presentation

import androidx.compose.runtime.Stable

@Stable
data class CalculateDepositUiState(
    val incomeRatio: Float? = null,
    val depositAmount: String = "",
    val income: String = "",
    val totalValue: String = ""
)