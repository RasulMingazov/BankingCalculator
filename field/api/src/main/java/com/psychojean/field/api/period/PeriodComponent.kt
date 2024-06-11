package com.psychojean.field.api.period

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.psychojean.core.PeriodType
import kotlinx.coroutines.flow.StateFlow

@Immutable
interface PeriodComponent {

    val text: StateFlow<String>

    val error: StateFlow<Int?>

    val value: StateFlow<Int>

    fun onChange(value: String)

    fun onPeriodTypeSelected(type: PeriodType)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            period: Int,
            periodType: PeriodType
        ): PeriodComponent
    }
}
