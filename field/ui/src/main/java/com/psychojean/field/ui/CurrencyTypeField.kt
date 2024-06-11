package com.psychojean.field.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psychojean.field.api.currency_type.CurrencyTypeComponent
import com.psychojean.field.ui.common.SelectField

@Composable
fun CurrencyTypeField(component: CurrencyTypeComponent, modifier: Modifier = Modifier) {

    val types by component.types.collectAsStateWithLifecycle()
    val selectedType by component.value.collectAsStateWithLifecycle()

    SelectField(modifier = modifier, items = types, selectedItem = selectedType, onSelected = component::onSelect)

}
