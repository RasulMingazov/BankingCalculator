package com.psychojean.core

import kotlinx.serialization.Serializable

@Serializable
enum class CurrencyType(val symbol: Char) { USD('$'), RUB('₽'), EUR('€'), CNY('¥'), KZT('₸') }