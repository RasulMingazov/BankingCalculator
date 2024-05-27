package com.psychojean.feature.deposit.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
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
import com.psychojean.core.ui.ThousandTransformation
import com.psychojean.feature.deposit.api.CurrencyType
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun InitialDepositField(
    modifier: Modifier = Modifier,
    state: CalculateDepositUiState = CalculateDepositUiState(),
    onAccept: (intent: CalculateDepositStore.Intent) -> Unit = {}
) {
    Row(modifier = modifier) {
        InitialDepositTextField(
            modifier = Modifier.weight(2f),
            initialDeposit = state.initialDeposit,
            error = state.initialDepositError,
            onInitialDepositChange = { onAccept(CalculateDepositStore.Intent.InitialDepositChanged(it)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        CurrencyTypeSelectField(
            modifier = Modifier.weight(1f),
            selectedCurrencyName = state.selectedCurrencyName,
            currencyTypes = state.currencyTypes,
            onCurrencySelect = { selectedCurrency ->
                onAccept(CalculateDepositStore.Intent.CurrencyTypeChanged(selectedCurrency))
            }
        )
    }
}

@Composable
private fun InitialDepositTextField(
    onInitialDepositChange: (value: String) -> Unit,
    initialDeposit: String,
    modifier: Modifier = Modifier,
    error: Int? = null,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier,
        value = initialDeposit,
        onValueChange = onInitialDepositChange,
        isError = error != null,
        maxLines = 1,
        visualTransformation = ThousandTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        label = {
            Text(
                text = stringResource(id = R.string.initial_deposit),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        supportingText = {
            if (error != null)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = error),
                    color = MaterialTheme.colorScheme.error
                )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyTypeSelectField(
    currencyTypes: ImmutableList<CurrencyType>,
    selectedCurrencyName: String,
    onCurrencySelect: (currency: CurrencyType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        CurrencyTypeTextField(modifier = Modifier.menuAnchor(), currencyName = selectedCurrencyName)
        CurrencyTypeDropDownMenu(
            expanded = expanded,
            currencyTypes = currencyTypes,
            onCurrencySelect = { type ->
                focusManager.moveFocus(FocusDirection.Next)
                onCurrencySelect(type)
            },
            onCloseMenu = { expanded = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExposedDropdownMenuBoxScope.CurrencyTypeDropDownMenu(
    expanded: Boolean,
    currencyTypes: ImmutableList<CurrencyType>,
    onCurrencySelect: (currency: CurrencyType) -> Unit,
    modifier: Modifier = Modifier,
    onCloseMenu: () -> Unit = {}
) {
    ExposedDropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onCloseMenu
    ) {
        currencyTypes.forEach { currencyType ->
            CurrencyTypeMenuItem(
                currencyType = currencyType,
                onCurrencySelect = { type ->
                    onCurrencySelect(type)
                    onCloseMenu()
                }
            )
        }
    }
}

@Composable
fun CurrencyTypeMenuItem(
    currencyType: CurrencyType,
    onCurrencySelect: (currency: CurrencyType) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        modifier = modifier,
        text = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = currencyType.name
            )
        },
        onClick = { onCurrencySelect(currencyType) }
    )
}

@Composable
fun CurrencyTypeTextField(
    currencyName: String,
    modifier: Modifier = Modifier,
    onValueChange: (value: String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        readOnly = true,
        onValueChange = onValueChange,
        value = currencyName,
        label = {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = stringResource(id = R.string.currency),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}
