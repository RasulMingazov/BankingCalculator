package com.psychojean.feature.deposit.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.psychojean.feature.deposit.api.PeriodType
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun PeriodField(
    modifier: Modifier = Modifier,
    state: CalculateDepositUiState = CalculateDepositUiState(),
    onAccept: (intent: CalculateDepositStore.Intent) -> Unit = {}
) {
    Row(modifier = modifier) {
        PeriodTextField(
            modifier = Modifier.weight(2f),
            period = state.period,
            error = state.periodError,
            onPeriodChange = { onAccept(CalculateDepositStore.Intent.PeriodChanged(it)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        PeriodTypeSelectField(
            modifier = Modifier.weight(1f),
            selectedPeriodName = state.selectedPeriodName,
            periodTypes = state.periodTypes,
            onPeriodTypeSelect = { selectedPeriodType ->
                onAccept(CalculateDepositStore.Intent.PeriodTypeChanged(selectedPeriodType))
            }
        )
    }
}

@Composable
fun PeriodTextField(
    modifier: Modifier = Modifier,
    period: String = "",
    error: Int? = null,
    onPeriodChange: (period: String) -> Unit = {},
) {
    OutlinedTextField(
        value = period,
        onValueChange = onPeriodChange,
        modifier = modifier,
        isError = error != null,
        supportingText = {
            if (error != null)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = error),
                    color = MaterialTheme.colorScheme.error
                )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        maxLines = 1,
        label = {
            Text(
                text = stringResource(id = R.string.period),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PeriodTypeSelectField(
    periodTypes: ImmutableList<PeriodType>,
    selectedPeriodName: String,
    onPeriodTypeSelect: (periodType: PeriodType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        PeriodTypeTextField(modifier = Modifier.menuAnchor(), periodTypeName = selectedPeriodName)
        PeriodTypeDropDownMenu(
            expanded = expanded,
            periodTypes = periodTypes,
            onPeriodTypeSelect = { type ->
                focusManager.moveFocus(FocusDirection.Next)
                onPeriodTypeSelect(type)
            },
            onCloseMenu = { expanded = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExposedDropdownMenuBoxScope.PeriodTypeDropDownMenu(
    expanded: Boolean,
    periodTypes: ImmutableList<PeriodType>,
    onPeriodTypeSelect: (periodType: PeriodType) -> Unit,
    modifier: Modifier = Modifier,
    onCloseMenu: () -> Unit = {}
) {
    ExposedDropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onCloseMenu
    ) {
        periodTypes.forEach { periodType ->
            PeriodTypeMenuItem(
                periodType = periodType,
                onPeriodTypeSelect = { type ->
                    onPeriodTypeSelect(type)
                    onCloseMenu()
                }
            )
        }
    }
}

@Composable
fun PeriodTypeMenuItem(
    periodType: PeriodType,
    onPeriodTypeSelect: (currency: PeriodType) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        modifier = modifier,
        text = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = periodType.name
            )
        },
        onClick = { onPeriodTypeSelect(periodType) }
    )
}

@Composable
fun PeriodTypeTextField(
    periodTypeName: String,
    modifier: Modifier = Modifier,
    onValueChange: (value: String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        readOnly = true,
        onValueChange = onValueChange,
        value = periodTypeName,
        label = {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = stringResource(id = R.string.period),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}
