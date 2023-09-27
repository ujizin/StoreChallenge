package com.ujizin.storechallenge.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Double,
    val price: Double,
    val discountPercentage: Double,
    val stock: Int,
    val images: List<String>
)