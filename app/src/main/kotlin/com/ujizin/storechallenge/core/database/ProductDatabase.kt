package com.ujizin.storechallenge.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ujizin.storechallenge.core.database.dao.ProductDao
import com.ujizin.storechallenge.core.database.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        const val DATABASE_NAME = "via_verde"
    }
}