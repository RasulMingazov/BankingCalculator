package com.psychojean.field.api.period.validation.month

interface MonthPeriodValidationUseCase {

    suspend operator fun invoke(month: String): Result<Int>

}
