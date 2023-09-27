package com.ujizin.storechallenge.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("products")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    @ColumnInfo("image_url")
    val imageUrl: String,
    val rating: Double,
    val price: Double,
    @ColumnInfo("discount_percentage")
    val discountPercentage: Double,
    val stock: Int,
)
