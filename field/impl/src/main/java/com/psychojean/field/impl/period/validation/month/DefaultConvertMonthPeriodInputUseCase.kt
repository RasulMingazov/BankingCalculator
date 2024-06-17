package com.psychojean.field.impl.period.validation.month

import com.psychojean.field.api.period.validation.month.InvalidMonthPeriodException
import com.psychojean.field.api.period.validation.month.InvalidMonthPeriodType
import com.psychojean.field.api.period.validation.month.ConvertMonthPeriodInputUseCase
import java.math.BigInteger

internal class DefaultConvertMonthPeriodInputUseCase(
) : ConvertMonthPeriodInputUseCase {

    override suspend operator fun invoke(month: String): Result<Int> = runCatching {
        if (month.isEmpty()) throw InvalidMonthPeriodException(InvalidMonthPeriodType.EMPTY)
        if (month.any { it == ',' || it == '.' }) throw InvalidMonthPeriodException(
            InvalidMonthPeriodType.CONTAINS_DOT_OR_COMMA
        )
        val monthBigValue = month.toBigIntegerOrNull() ?: throw InvalidMonthPeriodException(
            InvalidMonthPeriodType.NOT_A_NUMBER
        )
        if (monthBigValue < BigInteger.ONE) throw InvalidMonthPeriodException(InvalidMonthPeriodType.LESS_THAN_ONE)
        if (monthBigValue > BigInteger.valueOf(120)) throw InvalidMonthPeriodException(
            InvalidMonthPeriodType.MORE_THAN_120
        )
        monthBigValue.toInt()
    }
}
