package com.ujizin.storechallenge.core.data.dispatcher

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: ProductDispatcher)

enum class ProductDispatcher {
    IO
}