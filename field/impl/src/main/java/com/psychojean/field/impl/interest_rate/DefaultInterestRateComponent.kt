package com.psychojean.field.impl.interest_rate

import androidx.annotation.StringRes
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.field.api.interest_rate.InterestRateComponent
import com.psychojean.field.api.interest_rate.ConvertInterestInputUseCase
import com.psychojean.field.api.interest_rate.InvalidInterestRateException
import com.psychojean.field.api.interest_rate.InvalidInterestRateType
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

internal class DefaultInterestRateComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted rate: Double,
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

    private val _text = MutableStateFlow(defaultSaved.text)
    override val text: StateFlow<String> = _text.asStateFlow()

    private val _error = MutableStateFlow(defaultSaved.error)
    override val error: StateFlow<Int?> = _error.asStateFlow()

    private val _value = MutableStateFlow(defaultSaved.value)
    override val value: StateFlow<Double> = _value.asStateFlow()

    init {
        lifecycle.doOnDestroy(scope::cancel)

        stateKeeper.register(
            key = this::class.java.name,
            strategy = InterestRateSerializable.serializer(),
            supplier = {
                InterestRateSerializable(
                    value = value.value,
                    text = text.value,
                    error = error.value
                )
            }
        )
    }

    override fun onChange(value: String) {
        scope.launch {
            val preprocessInterestRate = value.take(4)
            _text.update { preprocessInterestRate }
            convertInterestInputUseCase(preprocessInterestRate).onSuccess { rate ->
                _error.update { null }
                _value.update { rate }
            }.onFailure { exception ->
                if (exception is InvalidInterestRateException) _error.update { exception.type.text }
            }
        }
    }

    @AssistedFactory
    interface Factory : InterestRateComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            rate: Double
        ): DefaultInterestRateComponent
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
