package com.ujizin.storechallenge.core.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ujizin.storechallenge.core.data.mapper.toEntity
import com.ujizin.storechallenge.core.database.ProductDatabase
import com.ujizin.storechallenge.core.database.dao.ProductDao
import com.ujizin.storechallenge.core.database.entity.ProductEntity
import com.ujizin.storechallenge.core.network.ProductService
import com.ujizin.storechallenge.core.network.dto.ProductResponse

@OptIn(ExperimentalPagingApi::class)
class ProductMediator(
    private val productService: ProductService,
    private val productDatabase: ProductDatabase,
    private val productDao: ProductDao,
) : RemoteMediator<Int, ProductEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {

        val skip = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.id ?: 0
            }
        }

        val products = productService
            .fetch(
                skip = skip,
                limit = state.config.pageSize,
            )
            .products
            .map(ProductResponse::toEntity)

        productDatabase.withTransaction { productDao.upsertAll(products) }

        return MediatorResult.Success(endOfPaginationReached = products.isEmpty())
    }
}
