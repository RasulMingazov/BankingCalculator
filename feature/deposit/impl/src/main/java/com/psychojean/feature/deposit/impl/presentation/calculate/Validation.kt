package com.psychojean.feature.deposit.impl.presentation.calculate

import androidx.annotation.StringRes
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestRateValidationError
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationError
import com.psychojean.feature.deposit.impl.R

@get:StringRes
internal val InterestRateValidationError?.text: Int?
    get() = when (this) {
        InterestRateValidationError.LESS_THAN_0 -> R.string.must_be_more_than_zero
        InterestRateValidationError.MORE_THAN_100 -> R.string.must_be_less_than_100_120
        InterestRateValidationError.NOT_A_NUMBER -> R.string.must_be_number
        InterestRateValidationError.EMPTY -> R.string.should_not_be_empty
        else -> null
    }

@get:StringRes
internal val AmountValidationError?.text: Int?
    get() = when (this) {
        AmountValidationError.NOT_A_NUMBER -> R.string.must_be_number
        AmountValidationError.CONTAINS_DOT_OR_COMMA -> R.string.should_not_contain_dots_or_commas
        AmountValidationError.MORE_THAN_1_BILLION -> R.string.must_be_less_than_one_billion
        AmountValidationError.EMPTY -> R.string.should_not_be_empty
        AmountValidationError.LESS_THAN_1 -> R.string.must_be_more_than_one
        else -> null
    }

@get:StringRes
internal val MonthPeriodValidationError?.text: Int?
    get() = when (this) {
        MonthPeriodValidationError.EMPTY -> R.string.should_not_be_empty
        MonthPeriodValidationError.NOT_A_NUMBER-> R.string.must_be_number
        MonthPeriodValidationError.LESS_THAN_1 -> R.string.must_be_more_than_one
        MonthPeriodValidationError.MORE_THAN_120 -> R.string.must_be_less_than_120
        MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA -> R.string.should_not_contain_dots_or_commas
        else -> null
    }