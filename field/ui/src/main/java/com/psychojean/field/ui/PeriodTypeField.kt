package com.psychojean.field.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psychojean.field.api.period_type.PeriodTypeComponent
import com.psychojean.field.ui.common.SelectField

@Composable
fun PeriodTypeField(component: PeriodTypeComponent, modifier: Modifier = Modifier) {

    val types by component.types.collectAsStateWithLifecycle()
    val selectedType by component.value.collectAsStateWithLifecycle()

    SelectField(modifier = modifier, items = types, selectedItem = selectedType, onSelected = component::onSelect)

}
