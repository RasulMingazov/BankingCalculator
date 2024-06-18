package com.psychojean.field.api.period.validation.month

interface ConvertMonthPeriodInputUseCase {

    suspend operator fun invoke(month: String): Result<Int>

}
