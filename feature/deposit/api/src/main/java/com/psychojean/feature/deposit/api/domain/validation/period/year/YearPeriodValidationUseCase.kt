package com.psychojean.feature.deposit.api.domain.validation.period.year

import com.psychojean.core.Error
import com.psychojean.core.RootResult

interface YearPeriodValidationUseCase {

    suspend operator fun invoke(year: String): RootResult<Unit, YearPeriodValidationError>

}

enum class YearPeriodValidationError :
    Error { EMPTY, CONTAINS_DOT_OR_COMMA, NOT_A_NUMBER, LESS_THAN_1, MORE_THAN_10 }
