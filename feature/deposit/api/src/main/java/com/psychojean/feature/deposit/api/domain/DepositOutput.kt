package com.psychojean.feature.deposit.api.domain

import java.math.BigDecimal

data class DepositOutput(
    val income: BigDecimal,
    val endAmount: BigDecimal
)
