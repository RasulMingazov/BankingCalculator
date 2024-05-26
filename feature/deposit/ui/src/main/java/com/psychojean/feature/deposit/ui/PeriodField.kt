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

@Composable
fun PeriodTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    error: Int? = null,
    onValueChange: (value: String) -> Unit = {},
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
