package com.psychojean.field.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.psychojean.field.api.period_type.PeriodTypeComponent
import com.psychojean.field.ui.common.SelectField

@Composable
fun PeriodTypeField(component: PeriodTypeComponent, modifier: Modifier = Modifier) {

    val types by component.types.subscribeAsState()
    val selectedType by component.value.subscribeAsState()

    SelectField(modifier = modifier, items = types, selectedItem = selectedType, onSelected = component::onSelect)

}
