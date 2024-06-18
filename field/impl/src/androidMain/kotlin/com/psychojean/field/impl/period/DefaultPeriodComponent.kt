package com.psychojean.field.impl.period

import androidx.annotation.StringRes
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.field.api.ErrorTextRes
import com.psychojean.core.PeriodType
import com.psychojean.field.api.period.PeriodComponent
import com.psychojean.field.api.period.validation.ConvertPeriodInputUseCase
import com.psychojean.field.api.period.validation.month.InvalidMonthPeriodException
import com.psychojean.field.api.period.validation.month.InvalidMonthPeriodType
import com.psychojean.field.api.period.validation.year.InvalidYearPeriodException
import com.psychojean.field.api.period.validation.year.InvalidYearType
import com.psychojean.field.impl.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

internal class DefaultPeriodComponent(
    componentContext: ComponentContext,
    period: Int,
    periodType: PeriodType,
    private val convertPeriodInputUseCase: ConvertPeriodInputUseCase,
) : PeriodComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val defaultSaved = stateKeeper.consume(
        key = this::class.java.name,
        strategy = PeriodSerializable.serializer()
    ) ?: PeriodSerializable(
        value = period,
        type = periodType,
        text = period.toString(),
        error = null
    )

    @Volatile
    private var periodType: PeriodType = defaultSaved.type

    private val _text = MutableValue(defaultSaved.text)
    override val text: Value<String> = _text

    private val _error = MutableValue(ErrorTextRes(defaultSaved.error))
    override val error: Value<ErrorTextRes> = _error

    private val _value = MutableValue(defaultSaved.value)
    override val value: Value<Int> = _value

    init {
        lifecycle.doOnDestroy(scope::cancel)

        stateKeeper.register(
            key = this::class.java.name,
            strategy = PeriodSerializable.serializer(),
            supplier = {
                PeriodSerializable(
                    value = value.value,
                    type = periodType,
                    text = text.value,
                    error = error.value.res
                )
            }
        )
    }

    override fun onChange(value: String) {
        scope.launch {
            _text.update { value.take(4) }
            convertPeriodInputUseCase(period = text.value, periodType = periodType)
                .onSuccess { period ->
                    _error.update { state -> state.copy(null) }
                    _value.update { period }
                }.onFailure { exception ->
                    if (exception is InvalidYearPeriodException) _error.update { state -> state.copy(exception.type.text) }
                    if (exception is InvalidMonthPeriodException) _error.update { state -> state.copy(exception.type.text) }
                }
        }
    }

    override fun onPeriodTypeSelected(type: PeriodType) {
        periodType = type
        onChange(text.value)
    }

    class Factory(private val convertPeriodInputUseCase: ConvertPeriodInputUseCase) : PeriodComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            period: Int,
            periodType: PeriodType
        ): PeriodComponent = DefaultPeriodComponent(
            componentContext = componentContext,
            period = period,
            periodType = periodType,
            convertPeriodInputUseCase = convertPeriodInputUseCase)
    }
}

@Serializable
private data class PeriodSerializable(
    val value: Int,
    val type: PeriodType,
    val text: String,
    val error: Int?
)

@get:StringRes
internal val InvalidMonthPeriodType?.text: Int?
    get() = when (this) {
        InvalidMonthPeriodType.EMPTY -> R.string.should_not_be_empty
        InvalidMonthPeriodType.NOT_A_NUMBER -> R.string.must_be_number
        InvalidMonthPeriodType.LESS_THAN_ONE -> R.string.must_be_more_than_one
        InvalidMonthPeriodType.MORE_THAN_120 -> R.string.must_be_less_than_120
        InvalidMonthPeriodType.CONTAINS_DOT_OR_COMMA -> R.string.should_not_contain_dots_or_commas
        else -> null
    }

@get:StringRes
internal val InvalidYearType?.text: Int?
    get() = when (this) {
        InvalidYearType.EMPTY -> R.string.should_not_be_empty
        InvalidYearType.NOT_A_NUMBER -> R.string.must_be_number
        InvalidYearType.LESS_THAN_ONE -> R.string.must_be_more_than_one
        InvalidYearType.MORE_THAN_TEN -> R.string.must_be_less_than_10
        InvalidYearType.CONTAINS_DOT_OR_COMMA -> R.string.should_not_contain_dots_or_commas
        else -> null
    }