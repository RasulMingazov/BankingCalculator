package com.psychojean.bankingcalculator.di

import com.psychojean.core.di.CoreContainer
import com.psychojean.core.di.DefaultCoreContainer
import com.psychojean.feature.deposit.api.di.DepositContainer
import com.psychojean.feature.deposit.impl.di.DefaultDepositContainer
import com.psychojean.field.api.di.FieldContainer
import com.psychojean.field.impl.di.DefaultFieldContainer
import com.psychojean.root.api.di.RootContainer
import com.psychojean.root.impl.di.DefaultRootContainer

internal class DefaultAppContainer : AppContainer {

    override val coreContainer: CoreContainer
        get() = DefaultCoreContainer()

    override val fieldContainer: FieldContainer
        get() = DefaultFieldContainer()

    override val depositContainer: DepositContainer
        get() = DefaultDepositContainer(
            coreContainer,
            fieldContainer
        )

    override val rootContainer: RootContainer
        get() = DefaultRootContainer(depositContainer)
}