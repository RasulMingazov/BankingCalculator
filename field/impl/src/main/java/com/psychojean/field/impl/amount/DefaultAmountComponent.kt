package com.psychojean.field.impl.amount

import androidx.annotation.StringRes
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.core.SerializableBigInteger
import com.psychojean.field.api.amount.AmountComponent
import com.psychojean.field.api.amount.AmountValidationError
import com.psychojean.field.api.amount.AmountValidationUseCase
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
import java.math.BigInteger

internal class DefaultAmountComponent @AssistedInject constructor(
    private val amountValidationUseCase: AmountValidationUseCase,
    @Assisted componentContext: ComponentContext,
    @Assisted amount: BigInteger
) : AmountComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val defaultSaved = stateKeeper.consume(
        key = this::class.java.name,
        strategy = AmountSerializable.serializer()
    ) ?: AmountSerializable(
        value = amount,
        text = amount.toString(),
        error = null
    )

    private val _text = MutableStateFlow(defaultSaved.text)
    override val text: StateFlow<String> = _text.asStateFlow()

    private val _error = MutableStateFlow(defaultSaved.error)
    override val error: StateFlow<Int?> = _error.asStateFlow()

    private val _value = MutableStateFlow(defaultSaved.value)
    override val value: StateFlow<BigInteger> = _value.asStateFlow()

    init {
        lifecycle.doOnDestroy(scope::cancel)

        stateKeeper.register(
            key = this::class.java.name,
            strategy = AmountSerializable.serializer(),
            supplier = {
                AmountSerializable(
                    value = value.value,
                    text = text.value,
                    error = error.value
                )
            }
        )
    }

    override fun onChange(value: String) {
        scope.launch {
            val preprocessAmount = value.filter { it.isDigit() }.take(10)
            _text.update { preprocessAmount }
            amountValidationUseCase(preprocessAmount).onSuccess { rate ->
                _error.update { null }
                _value.update { rate }
            }.onFailure { error ->
                _error.update { error.text }
            }
        }
    }

    @AssistedFactory
    interface Factory : AmountComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            amount: BigInteger
        ): DefaultAmountComponent
    }
}

@Serializable
private data class AmountSerializable(
    val value: SerializableBigInteger,
    val text: String,
    val error: Int?
)

@get:StringRes
private val AmountValidationError?.text: Int?
    get() = when (this) {
        AmountValidationError.NOT_A_NUMBER -> R.string.must_be_number
        AmountValidationError.CONTAINS_DOT_OR_COMMA -> R.string.should_not_contain_dots_or_commas
        AmountValidationError.MORE_THAN_1_BILLION -> R.string.must_be_less_than_one_billion
        AmountValidationError.EMPTY -> R.string.should_not_be_empty
        AmountValidationError.LESS_THAN_1 -> R.string.must_be_more_than_one
        else -> null
    }