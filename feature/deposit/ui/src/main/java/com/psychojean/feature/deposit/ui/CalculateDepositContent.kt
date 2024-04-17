package com.psychojean.feature.deposit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.psychojean.core.ui.PlaceholderTransformation
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateDepositContent(component: CalculateDepositComponent, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val state = component.state.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                title = stringResource(id = R.string.deposit_calculator)
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            CalculateButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = component::onCalculate
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            InitialDepositTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = state.value.initialDeposit,
                error = state.value.initialDepositError,
                onValueChange = component::onInitialDepositChange
            )
            InterestRateTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = state.value.interestRate,
                error = state.value.interestRateError,
                onValueChange = component::onInterestRateChange
            )
            MonthPeriodTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = state.value.monthPeriod,
                error = state.value.monthPeriodError,
                onValueChange = component::onInterestRateChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            CalculateButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .alpha(0f)
            )
        }
    }
}

@Composable
fun CalculateButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.calculate),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun InitialDepositTextField(
    value: String,
    error: Int?,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        isError = error != null,
        visualTransformation = PlaceholderTransformation(stringResource(id = R.string.enter_initial_deposit)),
        supportingText = {
            if (error != null)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = error),
                    color = MaterialTheme.colorScheme.error
                )
        },
        label = {
            Text(
                text = stringResource(id = R.string.initial_deposit),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

@Composable
fun InterestRateTextField(
    value: String,
    error: Int?,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        isError = error != null,
        visualTransformation = PlaceholderTransformation(stringResource(id = R.string.enter_interest_rate)),
        supportingText = {
            if (error != null)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = error),
                    color = MaterialTheme.colorScheme.error
                )
        },
        label = {
            Text(
                text = stringResource(id = R.string.interest_rate),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

@Composable
fun MonthPeriodTextField(
    value: String,
    error: Int?,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        isError = error != null,
        visualTransformation = PlaceholderTransformation(stringResource(id = R.string.enter_period)),
        supportingText = {
            if (error != null)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = error),
                    color = MaterialTheme.colorScheme.error
                )
        },
        label = {
            Text(
                text = stringResource(id = R.string.months),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    )
}