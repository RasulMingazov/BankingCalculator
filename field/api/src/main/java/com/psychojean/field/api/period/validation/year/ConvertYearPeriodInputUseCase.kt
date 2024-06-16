package com.psychojean.field.api.period.validation.year

interface ConvertYearPeriodInputUseCase {

    suspend operator fun invoke(year: String): Result<Int>

}
