package com.ujizin.storechallenge.core.data.mapper

import com.ujizin.storechallenge.core.database.entity.ProductEntity
import com.ujizin.storechallenge.core.network.dto.ProductResponse
import com.ujizin.storechallenge.domain.Product
import kotlin.math.roundToInt

internal fun ProductEntity.toDomain() = Product(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl,
    rating = rating,
    price = price,
    discountPercentage = discountPercentage,
    stock = stock,
)

internal fun Product.toEntity() = ProductEntity(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl,
    rating = rating,
    price = price,
    discountPercentage = discountPercentage,
    stock = stock,
)

internal fun ProductResponse.toEntity() = ProductEntity(
    id = id,
    title = title,
    description = description,
    rating = rating,
    price = price,
    discountPercentage = discountPercentage,
    stock = stock,
    imageUrl = when (rating.roundToInt()) {
        in 0..<3 -> 0
        in 3..4 -> 1
        else -> 2
    }.let { images[it.coerceAtMost(images.size - 1)] }
)
