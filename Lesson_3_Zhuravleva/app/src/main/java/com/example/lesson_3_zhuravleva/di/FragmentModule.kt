package com.example.lesson_3_zhuravleva.di

import com.example.lesson_3_zhuravleva.presentation.ui.catalog.CatalogFragment
import com.example.lesson_3_zhuravleva.presentation.ui.order.OrderFragment
import com.example.lesson_3_zhuravleva.presentation.ui.product.ProductFragment
import com.example.lesson_3_zhuravleva.presentation.ui.product.size.SizesBottomSheetFragment
import com.example.lesson_3_zhuravleva.presentation.ui.settings.SettingsFragment
import com.example.lesson_3_zhuravleva.presentation.ui.sign_in.SignInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun signInFragment(): SignInFragment

    @ContributesAndroidInjector(modules = [ListenerModule::class])
    abstract fun catalogFragment(): CatalogFragment

    @ContributesAndroidInjector(modules = [ListenerModule::class])
    abstract fun productFragment(): ProductFragment

    @ContributesAndroidInjector(modules = [ListenerModule::class])
    abstract fun sizesBottomSheetFragment(): SizesBottomSheetFragment

    @ContributesAndroidInjector
    abstract fun orderFragment(): OrderFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment(): SettingsFragment

}
