package com.psychojean.feature.deposit.impl.domain.validation.amount

import com.psychojean.core.api.RootResult
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.math.BigDecimal

class DefaultAmountValidationUseCaseTest {

    private val useCase = DefaultAmountValidationUseCase()

    @Test
    fun `validate with empty string`() = runTest {
        val result = useCase.validate("")
        val expected =
            RootResult.Failure<BigDecimal, AmountValidationError>(AmountValidationError.EMPTY)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with zero amount`() = runTest {
        val result = useCase.validate("0")
        val expected =
            RootResult.Failure<BigDecimal, AmountValidationError>(AmountValidationError.LESS_THAN_1)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with one amount`() = runTest {
        val value = "1"
        val result = useCase.validate(value)
        val expected = RootResult.Success<BigDecimal, AmountValidationError>(value.toBigDecimal())
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() = runTest {
        val result = useCase.validate("abc")
        val expected =
            RootResult.Failure<BigDecimal, AmountValidationError>(AmountValidationError.INCORRECT)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() = runTest {
        val result = useCase.validate("43a1")
        val expected =
            RootResult.Failure<BigDecimal, AmountValidationError>(AmountValidationError.INCORRECT)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with negative amount`() = runTest {
        val result = useCase.validate("-100")
        val expected =
            RootResult.Failure<BigDecimal, AmountValidationError>(AmountValidationError.LESS_THAN_1)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() = runTest {
        val result = useCase.validate("-100000000000000000000000000000000000000000000000")
        val expected =
            RootResult.Failure<BigDecimal, AmountValidationError>(AmountValidationError.LESS_THAN_1)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() = runTest {
        val value = "100000000000000000000000000000000000000000000000"
        val result = useCase.validate(value)
        val expected = RootResult.Success<BigDecimal, AmountValidationError>(value.toBigDecimal())
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() = runTest {
        val result = useCase.validate("   100   ")
        val expected =
            RootResult.Failure<BigDecimal, AmountValidationError>(AmountValidationError.INCORRECT)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with comma-separated amount`() = runTest {
        val result = useCase.validate("1,000")
        val expected =
            RootResult.Failure<BigDecimal, AmountValidationError>(AmountValidationError.INCORRECT)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with dot-separated amount`() = runTest {
        val value = "1.000"
        val result = useCase.validate(value)
        val expected = RootResult.Success<BigDecimal, AmountValidationError>(value.toBigDecimal())
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() = runTest {
        val value = "100"
        val result = useCase.validate("100")
        val expected = RootResult.Success<BigDecimal, AmountValidationError>(value.toBigDecimal())
        TestCase.assertEquals(expected, result)
    }
}
