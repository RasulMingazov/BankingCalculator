package com.psychojean.field.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.psychojean.core.ui.ThousandTransformation
import com.psychojean.field.api.amount.AmountComponent

@Composable
fun AmountField(
    component: AmountComponent,
    modifier: Modifier = Modifier
) {
    val text by component.text.subscribeAsState()
    val error by component.error.subscribeAsState()

    OutlinedTextField(
        value = text,
        onValueChange = component::onChange,
        modifier = modifier,
        isError = error.res != null,
        supportingText = {
            error.res?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = it),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        visualTransformation = ThousandTransformation,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        label = {
            Text(
                text = stringResource(id = R.string.initial_deposit),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
    )
}
