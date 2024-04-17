package com.psychojean.feature.deposit.impl.domain.validation.interest

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationError
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.math.BigDecimal

class DefaultInterestValidationUseCaseTest {

    private val useCase = DefaultInterestValidationUseCase()

    @Test
    fun `validate with empty string`() = runTest {
        val result = useCase.validate("")
        val expected =
            RootResult.Failure<BigDecimal, InterestValidationError>(InterestValidationError.EMPTY)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with zero amount`() = runTest {
        val value = "0"
        val result = useCase.validate(value)
        val expected =
            RootResult.Success<Double, InterestValidationError>(value.toDouble())
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one interest`() = runTest {
        val value = "1"
        val result = useCase.validate(value)
        val expected = RootResult.Success<Double, InterestValidationError>(value.toDouble())
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one and comma interest`() = runTest {
        val value = "1.3"
        val result = useCase.validate(value)
        val expected = RootResult.Success<Double, InterestValidationError>(value.toDouble())
        assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() = runTest {
        val result = useCase.validate("abc")
        val expected =
            RootResult.Failure<Double, InterestValidationError>(InterestValidationError.INCORRECT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() = runTest {
        val result = useCase.validate("43a1")
        val expected =
            RootResult.Failure<Double, InterestValidationError>(InterestValidationError.INCORRECT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with negative amount`() = runTest {
        val result = useCase.validate("-10")
        val expected =
            RootResult.Failure<Double, InterestValidationError>(InterestValidationError.LESS_THAN_0)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() = runTest {
        val result = useCase.validate("-100000000000000000000000000000000000000000000000")
        val expected =
            RootResult.Failure<Double, InterestValidationError>(InterestValidationError.LESS_THAN_0)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() = runTest {
        val value = "100000000000000000000000000000000000000000000000"
        val result = useCase.validate(value)
        val expected =
            RootResult.Failure<Double, InterestValidationError>(InterestValidationError.MORE_THAN_100)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() = runTest {
        val result = useCase.validate("   10   ")
        val expected =
            RootResult.Failure<Double, InterestValidationError>(InterestValidationError.INCORRECT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with comma-separated amount`() = runTest {
        val result = useCase.validate("1,0")
        val expected =
            RootResult.Failure<Double, InterestValidationError>(InterestValidationError.INCORRECT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with dot-separated amount`() = runTest {
        val value = "1.000"
        val result = useCase.validate(value)
        val expected =
            RootResult.Success<Double, InterestValidationError>(value.toDouble())
        assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() = runTest {
        val value = "100"
        val result = useCase.validate(value)
        val expected = RootResult.Success<Double, InterestValidationError>(value.toDouble())
        assertEquals(expected, result)
    }
}
