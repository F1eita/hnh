package com.example.lesson_3_zhuravleva.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lesson_3_zhuravleva.data.db.ProductDatabase.Companion.DATABASE_VERSION

@Database(
    entities = [ProductEntity::class],
    version = DATABASE_VERSION,
)
abstract class ProductDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "database.db"
        const val DATABASE_VERSION = 1
    }

    abstract fun createInspectionDao(): ProductDao
}