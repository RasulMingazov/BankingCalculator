package com.psychojean.field.api.period.validation.month

import com.psychojean.core.Error
import com.psychojean.core.RootResult

interface MonthPeriodValidationUseCase {

    suspend operator fun invoke(month: String): RootResult<Int, MonthPeriodValidationError>

}

enum class MonthPeriodValidationError :
    Error { EMPTY, CONTAINS_DOT_OR_COMMA, NOT_A_NUMBER, LESS_THAN_1, MORE_THAN_120 }