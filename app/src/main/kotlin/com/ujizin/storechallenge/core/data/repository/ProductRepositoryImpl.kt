package com.ujizin.storechallenge.core.data.repository

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.room.withTransaction
import com.ujizin.storechallenge.core.data.dispatcher.Dispatcher
import com.ujizin.storechallenge.core.data.dispatcher.ProductDispatcher
import com.ujizin.storechallenge.core.data.mapper.toDomain
import com.ujizin.storechallenge.core.data.mapper.toEntity
import com.ujizin.storechallenge.core.database.ProductDatabase
import com.ujizin.storechallenge.core.database.dao.ProductDao
import com.ujizin.storechallenge.core.database.entity.ProductEntity
import com.ujizin.storechallenge.core.network.ProductService
import com.ujizin.storechallenge.core.network.dto.ProductResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val productDatabase: ProductDatabase,
    @Dispatcher(ProductDispatcher.IO) private val dispatcher: CoroutineDispatcher,
) : ProductRepository {

    private val productDao: ProductDao = productDatabase.productDao()

    private suspend fun isProductSynced() = productDao.getCount() == TOTAL_SIZE

    override fun syncProducts() = flow {
        if (isProductSynced()) return@flow emit(Result.success(Unit))

        val products = productService.fetch(limit = TOTAL_SIZE)
            .products
            .map(ProductResponse::toEntity)

        productDatabase.withTransaction {
            productDao.upsertAll(products)
        }

        emit(Result.success(Unit))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(dispatcher)

    override fun getProduct(id: Int) = flow {
        val product = productDao.findById(id).toDomain()
        emit(Result.success(product))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(dispatcher)

    override fun getProducts() = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = productDao::pagingSource
    ).flow.map { pagingData ->
        pagingData.map(ProductEntity::toDomain)
    }.flowOn(dispatcher)

    companion object {
        private const val PAGE_SIZE = 20

        @VisibleForTesting
        const val TOTAL_SIZE = 100
    }
}

