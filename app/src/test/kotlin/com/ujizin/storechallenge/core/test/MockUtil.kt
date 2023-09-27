package com.ujizin.storechallenge.core.test

import com.ujizin.storechallenge.core.network.dto.ProductPagination
import com.ujizin.storechallenge.core.network.dto.ProductResponse
import com.ujizin.storechallenge.domain.Product

object MockUtil {

    private fun mockProductResponse(index: Int) = ProductResponse(
        id = index,
        title = "title $index",
        description = "description $index",
        rating = 5.0,
        price = 500.0,
        discountPercentage = 10.0,
        stock = 10,
        images = listOf("")
    )

    private fun mockProductResponseList(limit: Int) = List(limit, ::mockProductResponse)

    fun mockProduct(index: Int) = Product(
        id = index,
        title = "title $index",
        description = "description $index",
        imageUrl = "",
        rating = 5.0,
        price = 500.0,
        discountPercentage = 10.0,
        stock = 10,
    )

    fun mockProductPagination(limit: Int) = ProductPagination(mockProductResponseList(limit))
}