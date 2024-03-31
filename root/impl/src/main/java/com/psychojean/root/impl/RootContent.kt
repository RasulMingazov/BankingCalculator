package com.psychojean.root.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.psychojean.feature.deposit.ui.CalculateDepositContent
import com.psychojean.root.api.RootComponent

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        modifier = modifier,
        stack = component.stack,
        animation = stackAnimation(animator = fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.CalculateDeposit -> CalculateDepositContent(component = child.component)
        }
    }
}
