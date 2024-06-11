package com.psychojean.field.api.interest_rate

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

@Immutable
interface InterestRateComponent {

    val text: StateFlow<String>

    val error: StateFlow<Int?>

    val value: StateFlow<Double>

    fun onChange(value: String)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            rate: Double
        ): InterestRateComponent
    }
}
