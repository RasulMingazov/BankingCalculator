package com.psychojean.feature.deposit.api

import com.arkivanov.decompose.ComponentContext

interface CalculateDepositComponent {

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): CalculateDepositComponent
    }

}
