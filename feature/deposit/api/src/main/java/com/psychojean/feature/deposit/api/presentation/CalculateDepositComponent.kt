package com.psychojean.feature.deposit.api.presentation

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import kotlinx.coroutines.flow.Flow

@Immutable
interface CalculateDepositComponent {

    val state: Flow<CalculateDepositUiState>

    fun accept(intent: CalculateDepositStore.Intent)

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): CalculateDepositComponent
    }

}
