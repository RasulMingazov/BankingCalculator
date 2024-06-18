package com.psychojean.bankingcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.psychojean.bankingcalculator.ui.theme.BankingCalculatorTheme
import com.psychojean.root.api.RootComponent
import com.psychojean.root.impl.RootContent

class MainActivity : ComponentActivity() {

    private val component: RootComponent by lazy {
        (application as BankingApp).appContainer.rootContainer.rootComponentFactory(
            defaultComponentContext()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                ContextCompat.getColor(this, R.color.transparent),
                ContextCompat.getColor(this, R.color.transparent)
            ),
            navigationBarStyle = SystemBarStyle.light(
                ContextCompat.getColor(this, R.color.transparent),
                ContextCompat.getColor(this, R.color.transparent)
            ),
        )
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BankingCalculatorTheme {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .imePadding()
                ) {
                    RootContent(component = component)
                }
            }
        }
    }
}