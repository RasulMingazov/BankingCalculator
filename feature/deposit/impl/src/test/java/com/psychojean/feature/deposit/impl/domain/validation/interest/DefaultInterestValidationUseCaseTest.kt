package com.psychojean.feature.deposit.impl.domain.validation.interest

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestRateValidationError
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.math.BigDecimal

class DefaultInterestValidationUseCaseTest {

    private val useCase =
        com.psychojean.field.interestrate.impl.domain.DefaultInterestValidationUseCase()

    @Test
    fun `validate with empty string`() = runTest {
        val result = useCase("")
        val expected =
            RootResult.Failure<BigDecimal, InterestRateValidationError>(InterestRateValidationError.EMPTY)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with zero amount`() = runTest {
        val result = useCase("0")
        val expected =
            RootResult.Success<Unit, InterestRateValidationError>(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one interest`() = runTest {
        val result = useCase("1")
        val expected = RootResult.Success<Unit, InterestRateValidationError>(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one and comma interest`() = runTest {
        val result = useCase("1.3")
        val expected = RootResult.Success<Unit, InterestRateValidationError>(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() = runTest {
        val result = useCase("abc")
        val expected =
            RootResult.Failure<Unit, InterestRateValidationError>(InterestRateValidationError.NOT_A_NUMBER)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() = runTest {
        val result = useCase("43a1")
        val expected =
            RootResult.Failure<Unit, InterestRateValidationError>(InterestRateValidationError.NOT_A_NUMBER)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with negative amount`() = runTest {
        val result = useCase("-10")
        val expected =
            RootResult.Failure<Unit, InterestRateValidationError>(InterestRateValidationError.LESS_THAN_0)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() = runTest {
        val result = useCase("-100000000000000000000000000000000000000000000000")
        val expected =
            RootResult.Failure<Unit, InterestRateValidationError>(InterestRateValidationError.LESS_THAN_0)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() = runTest {
        val result = useCase("100000000000000000000000000000000000000000000000")
        val expected =
            RootResult.Failure<Unit, InterestRateValidationError>(InterestRateValidationError.MORE_THAN_100)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() = runTest {
        val result = useCase("   10   ")
        val expected =
            RootResult.Failure<Unit, InterestRateValidationError>(InterestRateValidationError.NOT_A_NUMBER)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with comma-separated amount`() = runTest {
        val result = useCase("1,0")
        val expected =
            RootResult.Failure<Unit, InterestRateValidationError>(InterestRateValidationError.NOT_A_NUMBER)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with dot-separated amount`() = runTest {
        val result = useCase("1.000")
        val expected =
            RootResult.Success<Unit, InterestRateValidationError>(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() = runTest {
        val result = useCase("100")
        val expected = RootResult.Success<Unit, InterestRateValidationError>(Unit)
        assertEquals(expected, result)
    }
}
