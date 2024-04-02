package com.psychojean.feature.deposit.impl.domain.validation.month

import com.psychojean.core.api.RootResult
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationError
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultMonthPeriodValidationUseCaseTest {

    private val useCase = DefaultMonthPeriodValidationUseCase()

    @Test
    fun `validate with empty string`() = runTest {
        val result = useCase.validate("")
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.EMPTY)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with digit containing comma`() = runTest {
        val result = useCase.validate("12,3")
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with digit containing dot`() = runTest {
        val result = useCase.validate("12.3")
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with zero month`() = runTest {
        val result = useCase.validate("0")
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.LESS_THAN_1)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one amount`() = runTest {
        val value = "1"
        val result = useCase.validate(value)
        val expected = RootResult.Success<Int, MonthPeriodValidationError>(value.toInt())
        assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() = runTest {
        val result = useCase.validate("abc")
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.INCORRECT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() = runTest {
        val result = useCase.validate("43a1")
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.INCORRECT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with negative month`() = runTest {
        val result = useCase.validate("-100")
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.LESS_THAN_1)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() = runTest {
        val result = useCase.validate("-100000000000000000000000000000000000000000000000")
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.LESS_THAN_1)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() = runTest {
        val value = "100000000000000000000000000000000000000000000000"
        val result = useCase.validate(value)
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.MORE_THAN_60)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() = runTest {
        val result = useCase.validate("   100   ")
        val expected =
            RootResult.Failure<Int, MonthPeriodValidationError>(MonthPeriodValidationError.INCORRECT)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() = runTest {
        val value = "100"
        val result = useCase.validate("100")
        val expected = RootResult.Success<Int, MonthPeriodValidationError>(value.toInt())
        assertEquals(expected, result)
    }
}
