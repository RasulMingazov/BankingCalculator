package com.psychojean.field.impl.period_type

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.psychojean.core.PeriodType
import com.psychojean.field.api.period_type.PeriodTypeComponent
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

internal class DefaultPeriodTypeComponent(
    componentContext: ComponentContext,
    periodType: PeriodType,
    periodTypes: List<PeriodType>,
) : PeriodTypeComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val defaultSaved = stateKeeper.consume(
        key = this::class.java.name,
        strategy = PeriodTypeSerializable.serializer()
    ) ?: PeriodTypeSerializable(
        value = periodType,
        types = periodTypes
    )

    override val types: StateFlow<ImmutableList<PeriodType>> =
        MutableStateFlow(defaultSaved.types.toImmutableList())

    private val _value = MutableStateFlow(defaultSaved.value)
    override val value: StateFlow<PeriodType> = _value.asStateFlow()

    init {
        lifecycle.doOnDestroy(scope::cancel)

        stateKeeper.register(
            key = this::class.java.name,
            strategy = PeriodTypeSerializable.serializer(),
            supplier = {
                PeriodTypeSerializable(
                    value = value.value,
                    types = types.value
                )
            }
        )
    }

    override fun onSelect(type: PeriodType) {
        _value.update { type }
    }

    class Factory : PeriodTypeComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            periodType: PeriodType,
            periodTypes: List<PeriodType>
        ): PeriodTypeComponent = DefaultPeriodTypeComponent(componentContext, periodType, periodTypes)
    }
}

@Serializable
private data class PeriodTypeSerializable(
    val value: PeriodType,
    val types: List<PeriodType>
)
