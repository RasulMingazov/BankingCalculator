package com.psychojean.field.api.amount

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import java.math.BigInteger

@Immutable
interface AmountComponent {

    val text: StateFlow<String>

    val error: StateFlow<Int?>

    val value: StateFlow<BigInteger>

    fun onChange(value: String)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            amount: BigInteger
        ): AmountComponent
    }
}
