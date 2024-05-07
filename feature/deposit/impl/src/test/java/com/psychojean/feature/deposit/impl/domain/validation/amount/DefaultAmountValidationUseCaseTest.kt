package com.psychojean.feature.deposit.impl.domain.validation.amount

import com.psychojean.core.RootResult
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultAmountValidationUseCaseTest {

    private val useCase = DefaultAmountValidationUseCase()

    @Test
    fun `validate with empty string`() = runTest {
        val result = useCase("")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.EMPTY)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with zero amount`() = runTest {
        val result = useCase("0")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.LESS_THAN_1)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with one amount`() = runTest {
        val result = useCase("1")
        val expected = RootResult.Success<Unit, AmountValidationError>(Unit)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() = runTest {
        val result = useCase("abc")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.NOT_A_DIGIT)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() = runTest {
        val result = useCase("43a1")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.NOT_A_DIGIT)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with negative amount`() = runTest {
        val result = useCase("-100")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.LESS_THAN_1)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() = runTest {
        val result = useCase("-100000000000000000000000000000000000000000000000")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.LESS_THAN_1)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() = runTest {
        val result = useCase("100000000000000000000000000000000000000000000000")
        val expected = RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.MORE_THAN_1_BILLION)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() = runTest {
        val result = useCase("   100   ")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.NOT_A_DIGIT)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with comma-separated amount`() = runTest {
        val result = useCase("1,000")
        val expected =
            RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.CONTAINS_DOT_OR_COMMA)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with dot-separated amount`() = runTest {
        val result = useCase("1.000")
        val expected = RootResult.Failure<Unit, AmountValidationError>(AmountValidationError.CONTAINS_DOT_OR_COMMA)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() = runTest {
        val result = useCase("100")
        val expected = RootResult.Success<Unit, AmountValidationError>(Unit)
        TestCase.assertEquals(expected, result)
    }
}
