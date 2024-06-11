package com.psychojean.field.impl.period

import androidx.annotation.StringRes
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.core.PeriodType
import com.psychojean.field.api.period.PeriodComponent
import com.psychojean.field.api.period.validation.PeriodValidationUseCase
import com.psychojean.field.api.period.validation.month.MonthPeriodValidationError
import com.psychojean.field.api.period.validation.year.YearPeriodValidationError
import com.psychojean.field.impl.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

internal class DefaultPeriodComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted period: Int,
    @Assisted periodType: PeriodType,
    private val periodValidationUseCase: PeriodValidationUseCase,
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

    private val _text = MutableStateFlow(defaultSaved.text)
    override val text: StateFlow<String> = _text.asStateFlow()

    private val _error = MutableStateFlow(defaultSaved.error)
    override val error: StateFlow<Int?> = _error.asStateFlow()

    private val _value = MutableStateFlow(defaultSaved.value)
    override val value: StateFlow<Int> = _value.asStateFlow()

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
                    error = error.value
                )
            }
        )
    }

    override fun onChange(value: String) {
        scope.launch {
            val preprocessPeriod = value.take(4)
            _text.update { preprocessPeriod }
            periodValidationUseCase(preprocessPeriod, periodType = periodType).onSuccess { period ->
                _error.update { null }
                _value.update { period }
            }.onFailure { error ->
                _error.update {
                    error.yearPeriodValidationError.text ?: error.monthPeriodValidationError.text
                }
            }
        }
    }

    override fun onPeriodTypeSelected(type: PeriodType) {
        periodType = type
        onChange(text.value)
    }

    @AssistedFactory
    interface Factory : PeriodComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            period: Int,
            periodType: PeriodType
        ): DefaultPeriodComponent
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
internal val MonthPeriodValidationError?.text: Int?
    get() = when (this) {
        MonthPeriodValidationError.EMPTY -> R.string.should_not_be_empty
        MonthPeriodValidationError.NOT_A_NUMBER -> R.string.must_be_number
        MonthPeriodValidationError.LESS_THAN_1 -> R.string.must_be_more_than_one
        MonthPeriodValidationError.MORE_THAN_120 -> R.string.must_be_less_than_120
        MonthPeriodValidationError.CONTAINS_DOT_OR_COMMA -> R.string.should_not_contain_dots_or_commas
        else -> null
    }

@get:StringRes
internal val YearPeriodValidationError?.text: Int?
    get() = when (this) {
        YearPeriodValidationError.EMPTY -> R.string.should_not_be_empty
        YearPeriodValidationError.NOT_A_NUMBER -> R.string.must_be_number
        YearPeriodValidationError.LESS_THAN_1 -> R.string.must_be_more_than_one
        YearPeriodValidationError.MORE_THAN_10 -> R.string.must_be_less_than_10
        YearPeriodValidationError.CONTAINS_DOT_OR_COMMA -> R.string.should_not_contain_dots_or_commas
        else -> null
    }