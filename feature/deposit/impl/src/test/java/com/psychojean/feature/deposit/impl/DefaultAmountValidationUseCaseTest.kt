package com.psychojean.feature.deposit.impl

import com.psychojean.core.api.RootResult
import com.psychojean.feature.deposit.api.domain.AmountValidationError
import com.psychojean.feature.deposit.impl.domain.DefaultAmountValidationUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DefaultAmountValidationUseCaseTest {

    private val useCase = DefaultAmountValidationUseCase()

    @Test
    fun `validate with empty string`() {
        val result = useCase.validate("")
        val expected = RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.EMPTY)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with zero amount`() {
        val result = useCase.validate("0")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.LESS_THAN_ONE)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one amount`() {
        val result = useCase.validate("1")
        val expected = RootResult.Success<Unit, AmountValidationError>(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() {
        val result = useCase.validate("abc")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.NOT_DIGIT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() {
        val result = useCase.validate("43a1")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.NOT_DIGIT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with negative amount`() {
        val result = useCase.validate("-100")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.LESS_THAN_ONE)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() {
        val result = useCase.validate("-100000000000000000000000000000000000000000000000")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.LESS_THAN_ONE)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() {
        val result = useCase.validate("100000000000000000000000000000000000000000000000")
        val expected = RootResult.Success<Unit, AmountValidationError>(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() {
        val result = useCase.validate("   100   ")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.NOT_DIGIT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with comma-separated amount`() {
        val result = useCase.validate("1,000")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.NOT_DIGIT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with dot-separated amount`() {
        val result = useCase.validate("1.000")
        val expected = RootResult.Success<Unit, AmountValidationError>(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() {
        val result = useCase.validate("100")
        val expected = RootResult.Success<Unit, AmountValidationError>(Unit)
        assertEquals(expected, result)
    }

}
