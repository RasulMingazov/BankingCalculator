package com.psychojean.feature.deposit.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.psychojean.core.ui.PlaceholderTransformation
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.feature.deposit.api.presentation.CalculateDepositUiState

@Composable
fun InterestRateField(
    modifier: Modifier = Modifier,
    state: CalculateDepositUiState = CalculateDepositUiState(),
    onAccept: (intent: CalculateDepositStore.Intent) -> Unit = {}
) {
    InterestRateTextField(
        modifier = modifier,
        interestRate = state.interestRate,
        error = state.interestRateError,
        onInterestRateChange = { onAccept(CalculateDepositStore.Intent.InterestRateChanged(it)) }
    )
}

@Composable
private fun InterestRateTextField(
    interestRate: String,
    error: Int?,
    onInterestRateChange: (value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = interestRate,
        onValueChange = onInterestRateChange,
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
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        label = {
            Text(
                text = stringResource(id = R.string.interest_rate),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}
