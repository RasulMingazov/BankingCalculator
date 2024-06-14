package com.psychojean.field.impl.period.validation.month

import com.psychojean.field.api.period.validation.month.InvalidMonthPeriodException
import com.psychojean.field.api.period.validation.month.InvalidMonthPeriodType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultMonthPeriodValidationUseCaseTest {

    private val useCase = DefaultMonthPeriodValidationUseCase()

    @Test
    fun `validate with empty string`() = runTest {
        val result = useCase("")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.EMPTY))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with digit containing comma`() = runTest {
        val result = useCase("12,3")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.CONTAINS_DOT_OR_COMMA))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with digit containing dot`() = runTest {
        val result = useCase("12.3")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.CONTAINS_DOT_OR_COMMA))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with zero month`() = runTest {
        val result = useCase("0")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.LESS_THAN_ONE))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one amount`() = runTest {
        val value = "1"
        val result = useCase(value)
        val expected = Result.success(1)
        assertEquals(expected, result)
    }

    @Test
    fun `validate with non-digit string`() = runTest {
        val result = useCase("abc")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with one-digit char`() = runTest {
        val result = useCase("43a1")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with negative month`() = runTest {
        val result = useCase("-100")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.LESS_THAN_ONE))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large negative amount`() = runTest {
        val result = useCase("-100000000000000000000000000000000000000000000000")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.LESS_THAN_ONE))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with large positive amount`() = runTest {
        val result = useCase("100000000000000000000000000000000000000000000000")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.MORE_THAN_120))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with leading and trailing spaces`() = runTest {
        val result = useCase("   100   ")
        val expected =
            Result.failure<Int>(InvalidMonthPeriodException(InvalidMonthPeriodType.NOT_A_NUMBER))
        assertEquals(expected, result)
    }

    @Test
    fun `validate with positive amount`() = runTest {
        val result = useCase("100")
        val expected = Result.success(100)
        assertEquals(expected, result)
    }
}
