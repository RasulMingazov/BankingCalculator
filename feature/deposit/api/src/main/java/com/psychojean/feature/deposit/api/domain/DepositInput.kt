package com.psychojean.feature.deposit.api.domain

import java.math.BigDecimal

data class DepositInput(
    val initialDeposit: BigDecimal,
    val interest: Double,
    val monthPeriod: Int
)
