package com.psychojean.feature.deposit.api.domain

data class DepositInput(
    val initialDeposit: String,
    val interestRate: String,
    val monthPeriod: String
)
