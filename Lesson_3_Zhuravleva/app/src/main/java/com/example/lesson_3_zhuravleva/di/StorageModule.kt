package com.example.lesson_3_zhuravleva.di

import android.content.Context
import androidx.room.Room
import com.example.lesson_3_zhuravleva.data.db.ProductDao
import com.example.lesson_3_zhuravleva.data.db.ProductDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): ProductDatabase{
        return Room.databaseBuilder(context, ProductDatabase::class.java, "database.dp")
            .build()
    }

    @Provides
    fun provideDao(database: ProductDatabase): ProductDao{
        return database.createInspectionDao()
    }

}