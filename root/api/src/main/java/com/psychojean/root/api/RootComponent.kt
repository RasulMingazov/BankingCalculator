package com.psychojean.root.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
//        class Deposit(val component: DepositComponent) : Child()
    }

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }

}
