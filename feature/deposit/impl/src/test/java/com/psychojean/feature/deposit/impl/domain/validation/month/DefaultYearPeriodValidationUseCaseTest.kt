package com.psychojean.feature.deposit.impl.domain.validation.month

import com.psychojean.core.RootResult
import com.psychojean.field.api.period.validation.month.MonthPeriodValidationError
import com.psychojean.field.impl.period.month.DefaultMonthPeriodValidationUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultYearPeriodValidationUseCaseTest {

    private val useCase =
        com.psychojean.field.impl.period.month.DefaultMonthPeriodValidationUseCase()

    @Test
    fun `validate with empty string`() = runTest {
        val result = useCase("")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.EMPTY)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with digit containing comma`() = runTest {
        val result = useCase("12,3")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with digit containing dot`() = runTest {
        val result = useCase("12.3")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with zero month`() = runTest {
        val result = useCase("0")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.LESS_THAN_1)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one amount`() = runTest {
        val value = "1"
        val result = useCase(value)
        val expected = RootResult.Success<Unit, MonthPeriodValidationError>(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() = runTest {
        val result = useCase("abc")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.NOT_A_NUMBER)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() = runTest {
        val result = useCase("43a1")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.NOT_A_NUMBER)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with negative month`() = runTest {
        val result = useCase("-100")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.LESS_THAN_1)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() = runTest {
        val result = useCase("-100000000000000000000000000000000000000000000000")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.LESS_THAN_1)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() = runTest {
        val result = useCase("100000000000000000000000000000000000000000000000")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.MORE_THAN_120)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() = runTest {
        val result = useCase("   100   ")
        val expected =
            RootResult.Failure<Unit, MonthPeriodValidationError>(MonthPeriodValidationError.NOT_A_NUMBER)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() = runTest {
        val result = useCase("100")
        val expected = RootResult.Success<Unit, MonthPeriodValidationError>(Unit)
        assertEquals(expected, result)
    }
}
