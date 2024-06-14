package com.psychojean.field.api.period.validation.year

data class InvalidYearPeriodException(val type: InvalidYearType): IllegalArgumentException()

enum class InvalidYearType {
    EMPTY, CONTAINS_DOT_OR_COMMA, NOT_A_NUMBER, LESS_THAN_ONE, MORE_THAN_TEN
}
