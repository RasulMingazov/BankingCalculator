package com.psychojean.field.api.period_type

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.psychojean.core.PeriodType
import kotlinx.collections.immutable.ImmutableList

interface PeriodTypeComponent {

    val types: Value<ImmutableList<PeriodType>>

    val value: Value<PeriodType>

    fun onSelect(type: PeriodType)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            periodType: PeriodType,
            periodTypes: List<PeriodType>
        ): PeriodTypeComponent
    }
}
