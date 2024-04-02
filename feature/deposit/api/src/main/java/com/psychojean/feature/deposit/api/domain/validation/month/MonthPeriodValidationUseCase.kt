package com.psychojean.feature.deposit.api.domain.validation.month

import com.psychojean.core.api.Error
import com.psychojean.core.api.RootResult

interface MonthPeriodValidationUseCase {

    suspend fun validate(month: String): RootResult<Int, MonthPeriodValidationError>

}

enum class MonthPeriodValidationError : Error { EMPTY, CONTAINS_DOT_OR_COMMA, INCORRECT, LESS_THAN_1, MORE_THAN_60 }
