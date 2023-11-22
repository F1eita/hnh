package com.example.lesson_3_zhuravleva.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lesson_3_zhuravleva.domain.catalog.Product

@Entity(tableName = ProductDao.PRODUCT_ENTITY_NAME)
data class ProductEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("preview") val preview: String,
    @ColumnInfo("price") val price: Int,
    @ColumnInfo("department") val department: String
)

fun ProductEntity.toProduct(): Product{
    return Product(
        id = id,
        title = title,
        preview = preview,
        price = price,
        department = department
    )
}