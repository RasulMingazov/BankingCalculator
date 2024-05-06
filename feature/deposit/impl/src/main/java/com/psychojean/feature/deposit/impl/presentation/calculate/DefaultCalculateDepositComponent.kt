package com.psychojean.feature.deposit.impl.presentation.calculate

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import com.psychojean.feature.deposit.api.presentation.CalculateDepositIntent
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow

internal class DefaultCalculateDepositComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    private val calculateDepositViewModel: CalculateDepositViewModel.Factory,
) : CalculateDepositComponent, ComponentContext by componentContext {

    private val viewModel = instanceKeeper.getOrCreate { calculateDepositViewModel() }

    override val state: StateFlow<CalculateDepositUiState> = viewModel.state

    override fun accept(intent: CalculateDepositIntent) {
        viewModel.accept(intent)
    }

    @AssistedFactory
    interface Factory : CalculateDepositComponent.Factory {
        override fun invoke(componentContext: ComponentContext): DefaultCalculateDepositComponent
    }

}