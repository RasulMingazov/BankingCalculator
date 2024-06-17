package com.psychojean.field.api.currency_type

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.psychojean.core.CurrencyType
import kotlinx.collections.immutable.ImmutableList

@Immutable
interface CurrencyTypeComponent {

    val types: Value<ImmutableList<CurrencyType>>

    val value: Value<CurrencyType>

    fun onSelect(type: CurrencyType)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            currencyType: CurrencyType,
            currencyTypes: List<CurrencyType>
        ): CurrencyTypeComponent
    }
}
