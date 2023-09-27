package com.ujizin.storechallenge.core.data

import androidx.room.withTransaction
import com.ujizin.storechallenge.core.data.mapper.toEntity
import com.ujizin.storechallenge.core.data.repository.ProductRepository
import com.ujizin.storechallenge.core.data.repository.ProductRepositoryImpl
import com.ujizin.storechallenge.core.database.ProductDatabase
import com.ujizin.storechallenge.core.database.dao.ProductDao
import com.ujizin.storechallenge.core.network.ProductService
import com.ujizin.storechallenge.core.test.MainCoroutinesRule
import com.ujizin.storechallenge.core.test.MockUtil.mockProduct
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
}
