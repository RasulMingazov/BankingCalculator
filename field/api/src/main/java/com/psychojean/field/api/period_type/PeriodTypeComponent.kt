package com.psychojean.field.api.period_type

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.psychojean.core.PeriodType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.StateFlow

@Immutable
interface PeriodTypeComponent {

    val types: StateFlow<ImmutableList<PeriodType>>

    val value: StateFlow<PeriodType>

    fun onSelect(type: PeriodType)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            periodType: PeriodType,
            periodTypes: List<PeriodType>
        ): PeriodTypeComponent
    }
}
