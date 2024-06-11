package com.psychojean.field.api.currency_type

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.psychojean.core.CurrencyType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.StateFlow

@Immutable
interface CurrencyTypeComponent {

    val types: StateFlow<ImmutableList<CurrencyType>>

    val value: StateFlow<CurrencyType>

    fun onSelect(type: CurrencyType)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            currencyType: CurrencyType,
            currencyTypes: List<CurrencyType>
        ): CurrencyTypeComponent
    }
}
