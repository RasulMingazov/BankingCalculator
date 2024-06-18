package com.psychojean.feature.deposit.api.di

import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent

interface DepositContainer {

    val calculateDepositComponentFactory: CalculateDepositComponent.Factory

}