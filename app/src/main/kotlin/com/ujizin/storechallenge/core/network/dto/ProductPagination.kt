package com.ujizin.storechallenge.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductPagination(
    val products: List<ProductResponse>,
)
