package com.psychojean.field.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.psychojean.field.ui.R
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun <T : Enum<T>> SelectField(
    items: ImmutableList<T>,
    selectedItem: T,
    onSelected: (item: T) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextMenuField(modifier = Modifier.menuAnchor(), value = selectedItem.name)
        FieldDropDownMenu(
            expanded = expanded,
            items = items,
            onSelected = { item ->
                expanded = false
                focusManager.moveFocus(FocusDirection.Exit)
                onSelected(item)
            },
            onCloseMenu = { expanded = false }
        )
    }
}

@Composable
private fun TextMenuField(
    value: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        readOnly = true,
        onValueChange = { },
        value = value,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T : Enum<T>> ExposedDropdownMenuBoxScope.FieldDropDownMenu(
    expanded: Boolean,
    items: ImmutableList<T>,
    onSelected: (item: T) -> Unit,
    modifier: Modifier = Modifier,
    onCloseMenu: () -> Unit = {}
) {
    ExposedDropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onCloseMenu
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                modifier = modifier,
                text = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = item.name
                    )
                },
                onClick = { onSelected(item) }
            )
        }
    }
}
