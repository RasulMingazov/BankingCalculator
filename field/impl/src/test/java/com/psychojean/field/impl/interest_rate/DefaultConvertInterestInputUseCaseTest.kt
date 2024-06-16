package com.psychojean.field.impl.interest_rate

import com.psychojean.field.api.interest_rate.InvalidInterestRateException
import com.psychojean.field.api.interest_rate.InvalidInterestRateType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultConvertInterestInputUseCaseTest {

    private val useCase = DefaultConvertInterestInputUseCase()

    @Test
    fun `validate with empty string`() = runTest {
        val result = useCase("")
        val expected = Result.failure<Double>(InvalidInterestRateException(InvalidInterestRateType.EMPTY))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with zero amount`() = runTest {
        val result = useCase("0")
        val expected = Result.success(0.0)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one interest`() = runTest {
        val result = useCase("1")
        val expected = Result.success(1.0)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one and comma interest`() = runTest {
        val result = useCase("1.3")
        val expected = Result.success(1.3)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() = runTest {
        val result = useCase("abc")
        val expected = Result.failure<Double>(InvalidInterestRateException(InvalidInterestRateType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() = runTest {
        val result = useCase("43a1")
        val expected =  Result.failure<Double>(InvalidInterestRateException(InvalidInterestRateType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with negative amount`() = runTest {
        val result = useCase("-10")
        val expected =  Result.failure<Double>(InvalidInterestRateException(InvalidInterestRateType.LESS_THAN_ZERO))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() = runTest {
        val result = useCase("-100000000000000000000000000000000000000000000000")
        val expected = Result.failure<Double>(InvalidInterestRateException(InvalidInterestRateType.LESS_THAN_ZERO))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() = runTest {
        val result = useCase("100000000000000000000000000000000000000000000000")
        val expected =  Result.failure<Double>(InvalidInterestRateException(InvalidInterestRateType.MORE_THAN_ONE_HUNDRED))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() = runTest {
        val result = useCase("   10   ")
        val expected =  Result.failure<Double>(InvalidInterestRateException(InvalidInterestRateType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with comma-separated amount`() = runTest {
        val result = useCase("1,0")
        val expected =  Result.failure<Double>(InvalidInterestRateException(InvalidInterestRateType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with dot-separated amount`() = runTest {
        val result = useCase("1.000")
        val expected = Result.success(1.0)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() = runTest {
        val result = useCase("100")
        val expected = Result.success(100.0)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with slash`() = runTest {
        val result = useCase("-")
        val expected = Result.failure<Double>(InvalidInterestRateException(InvalidInterestRateType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }
}
