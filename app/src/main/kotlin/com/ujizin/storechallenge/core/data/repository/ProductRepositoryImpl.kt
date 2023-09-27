package com.ujizin.storechallenge.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.ujizin.storechallenge.core.data.dispatcher.Dispatcher
import com.ujizin.storechallenge.core.data.dispatcher.ProductDispatcher
import com.ujizin.storechallenge.core.data.mapper.toDomain
import com.ujizin.storechallenge.core.data.mediator.ProductMediator
import com.ujizin.storechallenge.core.database.ProductDatabase
import com.ujizin.storechallenge.core.database.dao.ProductDao
import com.ujizin.storechallenge.core.database.entity.ProductEntity
import com.ujizin.storechallenge.core.network.ProductService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val productDatabase: ProductDatabase,
    @Dispatcher(ProductDispatcher.IO) private val dispatcher: CoroutineDispatcher,
) : ProductRepository {

    private val productDao: ProductDao = productDatabase.productDao()

    override fun getProduct(id: Int) = flow {
        val product = productDao.findById(id).toDomain()
        emit(Result.success(product))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(dispatcher)

    override fun getProducts() = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        remoteMediator = ProductMediator(productService, productDatabase, productDao),
        pagingSourceFactory = productDao::pagingSource
    ).flow.map { pagingData ->
        pagingData.map(ProductEntity::toDomain)
    }.flowOn(dispatcher)

    companion object {
        private const val PAGE_SIZE = 20
    }
}

