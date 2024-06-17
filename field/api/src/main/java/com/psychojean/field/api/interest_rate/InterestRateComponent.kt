package com.psychojean.field.api.interest_rate

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.psychojean.field.api.ErrorTextRes

@Immutable
interface InterestRateComponent {

    val text: Value<String>

    val error: Value<ErrorTextRes>

    val value: Value<Double>

    fun onChange(value: String)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            rate: Double
        ): InterestRateComponent
    }
}
