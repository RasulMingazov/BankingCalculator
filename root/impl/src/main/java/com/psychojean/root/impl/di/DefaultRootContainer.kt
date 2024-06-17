package com.psychojean.root.impl.di

import com.psychojean.feature.deposit.api.di.DepositContainer
import com.psychojean.root.api.RootComponent
import com.psychojean.root.api.di.RootContainer
import com.psychojean.root.impl.DefaultRootComponent

class DefaultRootContainer(
    private val depositContainer: DepositContainer
) : RootContainer {

    override val rootComponentFactory: RootComponent.Factory
        get() = DefaultRootComponent.Factory(depositContainer.calculateDepositComponentFactory)

}