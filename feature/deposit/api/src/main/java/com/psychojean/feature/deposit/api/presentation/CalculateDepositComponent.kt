package com.psychojean.feature.deposit.api.presentation

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

@Immutable
interface CalculateDepositComponent {

    val state: StateFlow<CalculateDepositUiState>

    fun accept(intent: CalculateDepositIntent)

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): CalculateDepositComponent
    }

}
