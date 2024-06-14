package com.psychojean.field.api.amount

data class InvalidAmountException(val type: InvalidAmountType): IllegalArgumentException()

enum class InvalidAmountType {
    EMPTY, NOT_A_NUMBER, LESS_THAN_ONE, MORE_THAN_ONE_BILLION, CONTAINS_DOT_OR_COMMA
}
