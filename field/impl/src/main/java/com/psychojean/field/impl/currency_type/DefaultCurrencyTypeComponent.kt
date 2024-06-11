package com.psychojean.field.impl.currency_type

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.core.CurrencyType
import com.psychojean.field.api.currency_type.CurrencyTypeComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable

internal class DefaultCurrencyTypeComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted currencyType: CurrencyType,
    @Assisted currencyTypes: List<CurrencyType>,
) : CurrencyTypeComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val defaultSaved = stateKeeper.consume(
        key = this::class.java.name,
        strategy = CurrencyTypeSerializable.serializer()
    ) ?: CurrencyTypeSerializable(
        value = currencyType,
        types = currencyTypes,
    )

    override val types: StateFlow<ImmutableList<CurrencyType>> =
        MutableStateFlow(defaultSaved.types.toImmutableList())

    private val _value = MutableStateFlow(defaultSaved.value)
    override val value: StateFlow<CurrencyType> = _value.asStateFlow()

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

    @AssistedFactory
    interface Factory : CurrencyTypeComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            currencyType: CurrencyType,
            currencyTypes: List<CurrencyType>
        ): DefaultCurrencyTypeComponent
    }
}

@Serializable
private data class CurrencyTypeSerializable(
    val value: CurrencyType,
    val types: List<CurrencyType>
)
