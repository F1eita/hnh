package com.example.lesson_3_zhuravleva.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    companion object {
        const val PRODUCT_ENTITY_NAME = "product_entity"
    }

    @Query("SELECT * FROM $PRODUCT_ENTITY_NAME")
    suspend fun getProducts(): List<ProductEntity>

    @Insert(entity = ProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(productEntity: ProductEntity)

}