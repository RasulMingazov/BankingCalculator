package com.psychojean.feature.deposit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.presentation.CalculateDepositComponent
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateDepositContent(component: CalculateDepositComponent, modifier: Modifier = Modifier) {

    val state by component.state.collectAsStateWithLifecycle(CalculateDepositUiState())

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.deposit_profitability_calculator)
            )
        }
    ) { padding ->
        val lazyListState = rememberLazyListState()
        val visibilityOffset by remember {
            derivedStateOf {
                when {
                    lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && lazyListState.firstVisibleItemIndex == 0 -> {
                        val topSize = lazyListState.layoutInfo.visibleItemsInfo[0].size
                        val scrollOffset = lazyListState.firstVisibleItemScrollOffset
                        scrollOffset / topSize.toFloat()
                    }

                    else -> 1f
                }
            }
        }
        lazyListState.EnableDragScroll()

        val columnChildModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            state = lazyListState
        ) {
            item {
                CalculateDepositInputFields(
                    modifier = Modifier.graphicsLayer { alpha = 1f - visibilityOffset },
                    itemsModifier = columnChildModifier,
                    state = state,
                    onAccept = component::accept
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                CalculateDepositResult(
                    modifier = columnChildModifier.fillParentMaxSize(),
                    itemsModifier = columnChildModifier,
                    state = state,
                    onAccept = component::accept
                )
            }
        }

    }
}

@Composable
fun CalculateDepositResult(
    state: CalculateDepositUiState,
    onAccept: (intent: CalculateDepositStore.Intent) -> Unit,
    modifier: Modifier = Modifier,
    itemsModifier: Modifier = Modifier,

    ) {
    Box(modifier = modifier) {
        Card(modifier = itemsModifier) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = itemsModifier,
                text = stringResource(id = R.string.calculation_results),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(modifier = itemsModifier)
            Spacer(modifier = Modifier.height(8.dp))
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
    state: CalculateDepositUiState,
    onAccept: (intent: CalculateDepositStore.Intent) -> Unit,
    modifier: Modifier = Modifier,
    itemsModifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        InitialDepositField(
            modifier = itemsModifier,
            state = state,
            onAccept = onAccept
        )
        PeriodTextField(
            modifier = itemsModifier,
            value = state.monthPeriod,
            error = state.monthPeriodError,
            onValueChange = {
                onAccept(
                    CalculateDepositStore.Intent.PeriodChanged(it)
                )
            }
        )
        InterestRateField(
            modifier = itemsModifier,
            state = state,
            onAccept = onAccept
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, modifier: Modifier = Modifier) {
    LargeTopAppBar(
        modifier = modifier,
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

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun LazyListState.EnableDragScroll() {
    val isDragged by interactionSource.collectIsDraggedAsState()
    val isScrollingUp = isScrollingUp()
    LaunchedEffect(isDragged) {
        if (isDragged) return@LaunchedEffect
        if (firstVisibleItemIndex != 0) return@LaunchedEffect
        if (firstVisibleItemScrollOffset > layoutInfo.visibleItemsInfo[0].offset / 2 && !isScrollingUp)
            animateScrollToItem(1)
        else if (isScrollingUp)
            animateScrollToItem(0)
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}