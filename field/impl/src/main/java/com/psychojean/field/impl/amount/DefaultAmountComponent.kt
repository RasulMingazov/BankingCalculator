package com.psychojean.field.impl.amount

import androidx.annotation.StringRes
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.core.SerializableBigInteger
import com.psychojean.field.api.amount.AmountComponent
import com.psychojean.field.api.amount.ConvertAmountInputUseCase
import com.psychojean.field.api.amount.InvalidAmountException
import com.psychojean.field.api.amount.InvalidAmountType
import com.psychojean.field.impl.R
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

internal class DefaultAmountComponent(
    private val convertAmountInputUseCase: ConvertAmountInputUseCase,
    componentContext: ComponentContext,
    amount: BigInteger
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
            _text.update { value.filter { it.isDigit() }.take(10) }
            convertAmountInputUseCase(_text.value).onSuccess { rate ->
                _error.update { null }
                _value.update { rate }
            }.onFailure { exception ->
                if (exception is InvalidAmountException) _error.update { exception.type.text }
            }
        }
    }

    class Factory(private val convertAmountInputUseCase: ConvertAmountInputUseCase) :
        AmountComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            amount: BigInteger
        ): AmountComponent =
            DefaultAmountComponent(convertAmountInputUseCase, componentContext, amount)
    }
}

@Serializable
private data class AmountSerializable(
    val value: SerializableBigInteger,
    val text: String,
    val error: Int?
)

@get:StringRes
private val InvalidAmountType?.text: Int?
    get() = when (this) {
        InvalidAmountType.NOT_A_NUMBER -> R.string.must_be_number
        InvalidAmountType.CONTAINS_DOT_OR_COMMA -> R.string.should_not_contain_dots_or_commas
        InvalidAmountType.MORE_THAN_ONE_BILLION -> R.string.must_be_less_than_one_billion
        InvalidAmountType.EMPTY -> R.string.should_not_be_empty
        InvalidAmountType.LESS_THAN_ONE -> R.string.must_be_more_than_one
        else -> null
    }