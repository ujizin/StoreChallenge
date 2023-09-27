package com.ujizin.storechallenge.presentation.extensions

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun Double.formatToEuroCurrency(): String {
    val portugalLocale = Locale("pt", "PT")
    val euroFormat = NumberFormat.getCurrencyInstance(portugalLocale)
    euroFormat.currency = Currency.getInstance("EUR")

    return euroFormat.format(this)
}
