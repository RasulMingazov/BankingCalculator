package com.psychojean.feature.deposit.api.domain.validation

data class ValidateDeposit(
    val amount: String,
    val interest: String,
    val monthPeriod: String
)