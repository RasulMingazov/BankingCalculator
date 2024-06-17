package com.psychojean.field.api.period

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.psychojean.field.api.ErrorTextRes
import com.psychojean.core.PeriodType

@Immutable
interface PeriodComponent {

    val text: Value<String>

    val error: Value<ErrorTextRes>

    val value: Value<Int>

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
