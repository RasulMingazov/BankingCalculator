package com.psychojean.feature.deposit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState
import com.psychojean.field.api.amount.AmountComponent
import com.psychojean.field.api.currency_type.CurrencyTypeComponent
import com.psychojean.field.api.interest_rate.InterestRateComponent
import com.psychojean.field.api.period.PeriodComponent
import com.psychojean.field.api.period_type.PeriodTypeComponent
import com.psychojean.field.ui.AmountField
import com.psychojean.field.ui.CurrencyTypeField
import com.psychojean.field.ui.InterestRateField
import com.psychojean.field.ui.PeriodField
import com.psychojean.field.ui.PeriodTypeField

@Composable
fun CalculateDepositContent(component: CalculateDepositComponent, modifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    Scaffold(
        modifier = modifier,
        topBar = { TopBar() }
    ) { padding ->
        val columnChildModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            CalculateDepositTitle(itemsModifier = columnChildModifier)
            Spacer(modifier = Modifier.height(16.dp))
            CalculateDepositResult(
                itemsModifier = columnChildModifier,
                state = state
            )
            Spacer(modifier = Modifier.height(16.dp))
            CalculateDepositInputFields(
                amountComponent = component.amountComponent,
                interestRateComponent = component.interestRateComponent,
                periodComponent = component.periodComponent,
                periodTypeComponent = component.periodTypeComponent,
                currencyTypeComponent = component.currencyTypeComponent,
                itemsModifier = columnChildModifier,
            )
        }
    }
}

@Composable
fun CalculateDepositTitle(
    modifier: Modifier = Modifier,
    itemsModifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            modifier = itemsModifier,
            text = stringResource(id = R.string.deposit_profitability_calculator),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = itemsModifier,
            text = stringResource(id = R.string.calculate_deposit_description),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CalculateDepositResult(
    state: CalculateDepositUiState,
    modifier: Modifier = Modifier,
    itemsModifier: Modifier = Modifier,

    ) {
    Box(modifier = modifier) {
        Card(modifier = itemsModifier) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = itemsModifier) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.Red)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.deposit_amount)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = state.depositAmount,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = itemsModifier) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.income)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = state.income,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = itemsModifier) {
                Text(
                    text = stringResource(id = R.string.total_value)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = state.totalValue,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            state.incomeRatio?.let { ratio ->
                LinearProgressIndicator(
                    modifier = itemsModifier,
                    progress = ratio,
                    trackColor = Color.Red,
                    color = MaterialTheme.colorScheme.primary,
                    strokeCap = StrokeCap.Round
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun CalculateDepositInputFields(
    amountComponent: AmountComponent,
    periodComponent: PeriodComponent,
    interestRateComponent: InterestRateComponent,
    periodTypeComponent: PeriodTypeComponent,
    currencyTypeComponent: CurrencyTypeComponent,
    modifier: Modifier = Modifier,
    itemsModifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Row(modifier = itemsModifier) {
            AmountField(
                modifier = Modifier.weight(2f),
                component = amountComponent
            )
            Spacer(modifier = Modifier.width(8.dp))
            CurrencyTypeField(
                modifier = Modifier.weight(1f),
                component = currencyTypeComponent
            )
        }
        Row(modifier = itemsModifier) {
            PeriodField(
                modifier = Modifier.weight(2f),
                component = periodComponent
            )
            Spacer(modifier = Modifier.width(8.dp))
            PeriodTypeField(
                modifier = Modifier.weight(1f),
                component = periodTypeComponent
            )
        }
        InterestRateField(
            modifier = itemsModifier,
            component = interestRateComponent
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        }, title = {}
    )
}
