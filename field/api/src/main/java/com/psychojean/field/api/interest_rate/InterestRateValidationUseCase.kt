package com.psychojean.field.api.interest_rate

interface ConvertInterestInputUseCase {
    suspend operator fun invoke(value: String): Result<Double>
}
