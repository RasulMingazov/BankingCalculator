package com.psychojean.field.api.period.validation.year

interface YearPeriodValidationUseCase {

    suspend operator fun invoke(year: String): Result<Int>

}
