package com.psychojean.field.impl.amount

import androidx.annotation.StringRes
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.field.api.ErrorTextRes
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
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.math.BigInteger

internal class DefaultAmountComponent(
    componentContext: ComponentContext,
    amount: BigInteger,
    private val convertAmountInputUseCase: ConvertAmountInputUseCase
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

    private val _text = MutableValue(defaultSaved.text)
    override val text: Value<String> = _text

    private val _error = MutableValue(ErrorTextRes(defaultSaved.error))
    override val error: Value<ErrorTextRes> = _error

    private val _value = MutableValue(defaultSaved.value)
    override val value: Value<BigInteger> = _value

    init {
        lifecycle.doOnDestroy(scope::cancel)

        stateKeeper.register(
            key = this::class.java.name,
            strategy = AmountSerializable.serializer(),
            supplier = {
                AmountSerializable(
                    value = value.value,
                    text = text.value,
                    error = error.value.res
                )
            }
        )
    }

    override fun onChange(value: String) {
        scope.launch {
            _text.update { value.filter { it.isDigit() }.take(10) }
            convertAmountInputUseCase(_text.value).onSuccess { rate ->
                _error.update { state -> state.copy(null) }
                _value.update { rate }
            }.onFailure { exception ->
                if (exception is InvalidAmountException) _error.update { state ->
                    state.copy(exception.type.text)
                }
            }
        }
    }

    class Factory(private val convertAmountInputUseCase: ConvertAmountInputUseCase) :
        AmountComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            amount: BigInteger
        ): AmountComponent =
            DefaultAmountComponent(
                componentContext = componentContext,
                amount = amount,
                convertAmountInputUseCase = convertAmountInputUseCase,
            )
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