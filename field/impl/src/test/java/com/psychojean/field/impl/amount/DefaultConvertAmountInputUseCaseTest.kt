package com.psychojean.field.impl.amount

import com.psychojean.field.api.amount.InvalidAmountException
import com.psychojean.field.api.amount.InvalidAmountType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.math.BigInteger

class DefaultConvertAmountInputUseCaseTest {

    private val useCase = DefaultConvertAmountInputUseCase()

    @Test
    fun `validate with empty string`() = runTest {
        val result = useCase("")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.EMPTY))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with zero amount`() = runTest {
        val result = useCase("0")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.LESS_THAN_ONE))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one amount`() = runTest {
        val result = useCase("1")
        val expected = Result.success(BigInteger.ONE)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() = runTest {
        val result = useCase("abc")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() = runTest {
        val result = useCase("43a1")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with negative amount`() = runTest {
        val result = useCase("-100")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.LESS_THAN_ONE))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() = runTest {
        val result = useCase("-100000000000000000000000000000000000000000000000")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.LESS_THAN_ONE))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() = runTest {
        val result = useCase("100000000000000000000000000000000000000000000000")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.MORE_THAN_ONE_BILLION))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() = runTest {
        val result = useCase("   100   ")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with comma-separated amount`() = runTest {
        val result = useCase("1,000")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.CONTAINS_DOT_OR_COMMA))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with dot-separated amount`() = runTest {
        val result = useCase("1.000")
        val expected = Result.failure<BigInteger>(InvalidAmountException(InvalidAmountType.CONTAINS_DOT_OR_COMMA))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() = runTest {
        val result = useCase("100")
        val expected = Result.success(BigInteger.valueOf(100))
        assertEquals(expected, result)
    }
}
