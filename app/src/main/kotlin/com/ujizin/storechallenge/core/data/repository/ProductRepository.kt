package com.ujizin.storechallenge.core.data.repository

import androidx.paging.PagingData
import com.ujizin.storechallenge.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun syncProducts(): Flow<Result<Unit>>
    fun getProduct(id: Int): Flow<Result<Product>>
    fun getProducts(): Flow<PagingData<Product>>
}