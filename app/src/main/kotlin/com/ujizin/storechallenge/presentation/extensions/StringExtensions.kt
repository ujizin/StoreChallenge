package com.ujizin.storechallenge.presentation.extensions

fun String.toCapitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase() else it.toString()
}
