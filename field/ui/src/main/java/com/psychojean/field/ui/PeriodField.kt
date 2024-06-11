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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psychojean.field.api.period.PeriodComponent

@Composable
fun PeriodField(
    component: PeriodComponent,
    modifier: Modifier = Modifier
) {
    val text by component.text.collectAsStateWithLifecycle()
    val error by component.error.collectAsStateWithLifecycle()

    OutlinedTextField(
        value = text,
        onValueChange = component::onChange,
        modifier = modifier,
        isError = error != null,
        supportingText = {
            error?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = it),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        label = {
            Text(
                text = stringResource(id = R.string.period),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}
