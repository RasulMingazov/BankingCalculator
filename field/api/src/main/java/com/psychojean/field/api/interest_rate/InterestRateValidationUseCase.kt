package com.psychojean.field.api.interest_rate

interface InterestValidationUseCase {
    suspend operator fun invoke(value: String): Result<Double>
}
