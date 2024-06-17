package com.psychojean.field.api.amount

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.psychojean.field.api.ErrorTextRes
import java.math.BigInteger

@Immutable
interface AmountComponent {

    val text: Value<String>

    val error: Value<ErrorTextRes>

    val value: Value<BigInteger>

    fun onChange(value: String)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            amount: BigInteger
        ): AmountComponent
    }
}
