package com.ujizin.storechallenge.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ujizin.storechallenge.core.database.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM PRODUCTS WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): ProductEntity
    @Upsert
    suspend fun upsertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM PRODUCTS")
    fun pagingSource(): PagingSource<Int, ProductEntity>
}
