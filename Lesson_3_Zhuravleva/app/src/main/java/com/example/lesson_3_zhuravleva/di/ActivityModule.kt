package com.example.lesson_3_zhuravleva.di

import com.example.lesson_3_zhuravleva.presentation.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity
}