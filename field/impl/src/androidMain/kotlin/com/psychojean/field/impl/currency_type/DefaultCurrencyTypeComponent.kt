package com.psychojean.field.impl.currency_type

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.core.CurrencyType
import com.psychojean.field.api.currency_type.CurrencyTypeComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.serialization.Serializable

internal class DefaultCurrencyTypeComponent(
    componentContext: ComponentContext,
    currencyType: CurrencyType,
    currencyTypes: List<CurrencyType>,
) : CurrencyTypeComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val defaultSaved = stateKeeper.consume(
        key = this::class.java.name,
        strategy = CurrencyTypeSerializable.serializer()
    ) ?: CurrencyTypeSerializable(
        value = currencyType,
        types = currencyTypes,
    )

    override val types: Value<ImmutableList<CurrencyType>> =
        MutableValue(defaultSaved.types.toImmutableList())

    private val _value = MutableValue(defaultSaved.value)
    override val value: Value<CurrencyType> = _value

    init {
        lifecycle.doOnDestroy(scope::cancel)

        stateKeeper.register(
            key = this::class.java.name,
            strategy = CurrencyTypeSerializable.serializer(),
            supplier = {
                CurrencyTypeSerializable(
                    value = value.value,
                    types = types.value
                )
            }
        )
    }

    override fun onSelect(type: CurrencyType) {
        _value.update { type }
    }

    class Factory : CurrencyTypeComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            currencyType: CurrencyType,
            currencyTypes: List<CurrencyType>
        ): CurrencyTypeComponent = DefaultCurrencyTypeComponent(
            componentContext = componentContext,
            currencyType = currencyType,
            currencyTypes = currencyTypes
        )
    }
}

@Serializable
private data class CurrencyTypeSerializable(
    val value: CurrencyType,
    val types: List<CurrencyType>
)