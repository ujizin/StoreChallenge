package com.ujizin.storechallenge.core.network

import com.ujizin.storechallenge.core.network.dto.ProductPagination
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("/products")
    suspend fun fetch(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int
    ): ProductPagination
}