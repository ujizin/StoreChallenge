package com.ujizin.storechallenge.domain

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val rating: Double,
    val price: Double,
    val discountPercentage: Double,
    val stock: Int,
) {
    val originalPrice: Double
        get() = price / (1 - discountPercentage / 100.0)
}
