package com.example.lesson_3_zhuravleva.di

import android.content.Context
import com.example.lesson_3_zhuravleva.MyApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    fun provideApplicationContext(app: MyApplication): Context {
        return app.applicationContext
    }
}