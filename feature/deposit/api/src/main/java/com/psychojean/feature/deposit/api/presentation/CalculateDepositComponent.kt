package com.psychojean.feature.deposit.api.presentation

import com.arkivanov.decompose.ComponentContext

interface CalculateDepositComponent {

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): CalculateDepositComponent
    }

}
