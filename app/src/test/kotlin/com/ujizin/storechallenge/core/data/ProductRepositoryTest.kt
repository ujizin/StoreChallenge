package com.ujizin.storechallenge.core.data

import androidx.room.withTransaction
import com.ujizin.storechallenge.core.data.mapper.toEntity
import com.ujizin.storechallenge.core.data.repository.ProductRepository
import com.ujizin.storechallenge.core.data.repository.ProductRepositoryImpl
import com.ujizin.storechallenge.core.data.repository.ProductRepositoryImpl.Companion.TOTAL_SIZE
import com.ujizin.storechallenge.core.database.ProductDatabase
import com.ujizin.storechallenge.core.database.dao.ProductDao
import com.ujizin.storechallenge.core.network.ProductService
import com.ujizin.storechallenge.core.network.dto.ProductResponse
import com.ujizin.storechallenge.core.test.MainCoroutinesRule
import com.ujizin.storechallenge.core.test.MockUtil.mockProduct
import com.ujizin.storechallenge.core.test.MockUtil.mockProductPagination
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @MockK
    private lateinit var productDao: ProductDao

    @MockK
    private lateinit var database: ProductDatabase

    @MockK
    private lateinit var service: ProductService

    private lateinit var repository: ProductRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        mockkStatic("androidx.room.RoomDatabaseKt")

        val transactionLambda = slot<suspend () -> Any>()

        every { database.productDao() } returns productDao

        coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured()
        }

        repository = ProductRepositoryImpl(service, database, coroutinesRule.testDispatcher)
    }

    @Test
    fun findProductByIdInDatabaseTest() = runTest {
        val index = 1
        val expectedProduct = mockProduct(index)
        coEvery { productDao.findById(index) } returns expectedProduct.toEntity()

        repository.getProduct(index).collect { result ->
            Assert.assertEquals(expectedProduct, result.getOrThrow())
        }
    }

    @Test
    fun syncProductWhenProductListIsEmptyTest() = runTest {
        val mockProductPagination = mockProductPagination(TOTAL_SIZE)
        coEvery { productDao.getCount() } returns 0
        coEvery { service.fetch(limit = TOTAL_SIZE) } returns mockProductPagination

        repository.syncProducts().collect {
            coVerify { service.fetch(any(), any()) }
            coVerify { productDao.upsertAll(mockProductPagination.products.map(ProductResponse::toEntity)) }
        }
    }

    @Test
    fun ensureProductNotCalledByRemoteTest() = runTest {
        coEvery { productDao.getCount() } returns TOTAL_SIZE

        repository.syncProducts().collect {
            coVerify(exactly = 0) { service.fetch(limit = TOTAL_SIZE) }
        }
    }
}
