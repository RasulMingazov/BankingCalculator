package com.psychojean.field.api.period.validation.month

data class InvalidMonthPeriodException(val type: InvalidMonthPeriodType): IllegalArgumentException()

enum class InvalidMonthPeriodType {
    EMPTY, CONTAINS_DOT_OR_COMMA, NOT_A_NUMBER, LESS_THAN_ONE, MORE_THAN_120
}
