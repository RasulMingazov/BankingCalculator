package com.psychojean.field.api.interest_rate

data class InvalidInterestRateException(val type: InvalidInterestRateType): IllegalArgumentException()

enum class InvalidInterestRateType {
    EMPTY, NOT_A_NUMBER, LESS_THAN_ZERO, MORE_THAN_ONE_HUNDRED
}

