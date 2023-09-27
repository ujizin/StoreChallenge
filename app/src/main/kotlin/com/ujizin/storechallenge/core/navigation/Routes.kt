package com.ujizin.storechallenge.core.navigation

sealed class Screen(val route: String) {
    data object ProductList : Screen("/")
    data object ProductDetail : Screen("/detail/{${Argument.PRODUCT_ID}}") {
        fun withProductId(productId: Int) = withArguments(Argument.PRODUCT_ID to productId)
    }
}

fun Screen.withArguments(
    vararg arguments: Pair<String, Any>
): String = arguments.fold(route) { route, (key, value) ->
    route.replace("{$key}", "$value")
}
