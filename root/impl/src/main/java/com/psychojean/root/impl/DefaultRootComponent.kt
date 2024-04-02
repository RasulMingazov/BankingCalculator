package com.psychojean.root.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import com.psychojean.root.api.RootComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

class DefaultRootComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    private val calculateDepositFactory: CalculateDepositComponent.Factory
    ) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Deposit,
            serializer = Config.serializer(),
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, context: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Deposit -> RootComponent.Child.CalculateDeposit(
                calculateDepositComponent(
                    context
                )
            )
        }

    private fun calculateDepositComponent(context: ComponentContext): CalculateDepositComponent =
        calculateDepositFactory(componentContext = context)


    @Serializable
    sealed class Config {

        @Serializable
        data object Deposit : Config()
    }

    @AssistedFactory
    interface Factory : RootComponent.Factory {
        override fun invoke(componentContext: ComponentContext): DefaultRootComponent
    }
}
