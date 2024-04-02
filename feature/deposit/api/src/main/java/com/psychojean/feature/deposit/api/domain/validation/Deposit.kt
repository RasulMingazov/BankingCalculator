package com.psychojean.feature.deposit.api.domain.validation

data class Deposit(
    val amount: String,
    val monthPeriod: String
)