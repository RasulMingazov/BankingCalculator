package com.psychojean.feature.deposit.impl.presentation.calculate

import androidx.annotation.StringRes
import com.psychojean.feature.deposit.api.domain.validation.amount.AmountValidationError
import com.psychojean.feature.deposit.api.domain.validation.interest.InterestValidationError
import com.psychojean.feature.deposit.api.domain.validation.month.MonthPeriodValidationError
import com.psychojean.feature.deposit.impl.R

@get:StringRes
internal val InterestValidationError?.text: Int?
    get() = when (this) {
        InterestValidationError.LESS_THAN_0, InterestValidationError.MORE_THAN_100 -> R.string.interest_rate_must_be_between_0_and_100
        InterestValidationError.INCORRECT -> R.string.interest_rate_is_incorrect
        InterestValidationError.EMPTY -> R.string.interest_rate_cannot_be_empty
        else -> null
    }

@get:StringRes
internal val AmountValidationError?.text: Int?
    get() = when (this) {
        AmountValidationError.INCORRECT -> R.string.initial_deposit_is_incorrect
        AmountValidationError.EMPTY -> R.string.initial_deposit_cannot_be_empty
        AmountValidationError.LESS_THAN_1 -> R.string.initial_deposit_cannot_be_less_than_1
        else -> null
    }

@get:StringRes
internal val MonthPeriodValidationError?.text: Int?
    get() = when (this) {
        MonthPeriodValidationError.INCORRECT, MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA -> R.string.month_period_is_incorrect
        MonthPeriodValidationError.EMPTY -> R.string.month_period_cannot_be_empty
        MonthPeriodValidationError.LESS_THAN_1 -> R.string.month_period_cannot_be_less_than_1
        MonthPeriodValidationError.MORE_THAN_120 -> R.string.month_period_cannot_be_more_than_120
        else -> null
    }