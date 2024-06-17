package com.psychojean.bankingcalculator.di

import com.psychojean.core.di.CoreContainer
import com.psychojean.feature.deposit.api.di.DepositContainer
import com.psychojean.field.api.di.FieldContainer
import com.psychojean.root.api.di.RootContainer

interface AppContainer {
    val coreContainer: CoreContainer
    val fieldContainer: FieldContainer
    val depositContainer: DepositContainer
    val rootContainer: RootContainer
}
