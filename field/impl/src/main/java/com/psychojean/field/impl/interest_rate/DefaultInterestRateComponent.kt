package com.psychojean.field.impl.interest_rate

import androidx.annotation.StringRes
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.field.api.ErrorTextRes
import com.psychojean.field.api.interest_rate.ConvertInterestInputUseCase
import com.psychojean.field.api.interest_rate.InterestRateComponent
import com.psychojean.field.api.interest_rate.InvalidInterestRateException
import com.psychojean.field.api.interest_rate.InvalidInterestRateType
import com.psychojean.field.impl.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

internal class DefaultInterestRateComponent(
    componentContext: ComponentContext,
    rate: Double,
    private val convertInterestInputUseCase: ConvertInterestInputUseCase,
) : InterestRateComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val defaultSaved = stateKeeper.consume(
        key = this::class.java.name,
        strategy = InterestRateSerializable.serializer()
    ) ?: InterestRateSerializable(
        value = rate,
        text = rate.toString(),
        error = null
    )

    private val _text = MutableValue(defaultSaved.text)
    override val text: Value<String> = _text

    private val _error = MutableValue(ErrorTextRes(defaultSaved.error))
    override val error: Value<ErrorTextRes> = _error

    private val _value = MutableValue(defaultSaved.value)
    override val value: Value<Double> = _value

    init {
        lifecycle.doOnDestroy(scope::cancel)

        stateKeeper.register(
            key = this::class.java.name,
            strategy = InterestRateSerializable.serializer(),
            supplier = {
                InterestRateSerializable(
                    value = value.value,
                    text = text.value,
                    error = error.value.res
                )
            }
        )
    }

    override fun onChange(value: String) {
        scope.launch {
            val preprocessInterestRate = value.take(4)
            _text.update { preprocessInterestRate }
            convertInterestInputUseCase(preprocessInterestRate).onSuccess { rate ->
                _error.update { state -> state.copy(null) }
                _value.update { rate }
            }.onFailure { exception ->
                if (exception is InvalidInterestRateException) _error.update { state ->
                    state.copy(exception.type.text)
                }
            }
        }
    }

    class Factory(private val convertInterestInputUseCase: ConvertInterestInputUseCase) :
        InterestRateComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            rate: Double
        ): InterestRateComponent =
            DefaultInterestRateComponent(
                componentContext = componentContext,
                rate = rate,
                convertInterestInputUseCase = convertInterestInputUseCase
            )
    }
}

@Serializable
private data class InterestRateSerializable(
    val value: Double,
    val text: String,
    val error: Int?
)

@get:StringRes
private val InvalidInterestRateType?.text: Int?
    get() = when (this) {
        InvalidInterestRateType.LESS_THAN_ZERO -> R.string.must_be_more_than_zero
        InvalidInterestRateType.MORE_THAN_ONE_HUNDRED -> R.string.must_be_less_than_100_120
        InvalidInterestRateType.NOT_A_NUMBER -> R.string.must_be_number
        InvalidInterestRateType.EMPTY -> R.string.should_not_be_empty
        else -> null
    }
