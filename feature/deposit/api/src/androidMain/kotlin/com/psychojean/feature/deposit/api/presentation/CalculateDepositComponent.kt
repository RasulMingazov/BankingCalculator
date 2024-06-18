package com.psychojean.feature.deposit.api.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.psychojean.feature.deposit.api.domain.CalculateDepositStore
import com.psychojean.field.api.amount.AmountComponent
import com.psychojean.field.api.currency_type.CurrencyTypeComponent
import com.psychojean.field.api.interest_rate.InterestRateComponent
import com.psychojean.field.api.period.PeriodComponent
import com.psychojean.field.api.period_type.PeriodTypeComponent

interface CalculateDepositComponent {

    val state: Value<CalculateDepositUiState>

    val interestRateComponent: InterestRateComponent

    val amountComponent: AmountComponent

    val currencyTypeComponent: CurrencyTypeComponent

    val periodComponent: PeriodComponent

    val periodTypeComponent: PeriodTypeComponent

    fun accept(intent: CalculateDepositStore.Intent)

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): CalculateDepositComponent
    }

}
