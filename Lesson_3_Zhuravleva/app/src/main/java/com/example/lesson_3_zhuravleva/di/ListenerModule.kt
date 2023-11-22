package com.example.lesson_3_zhuravleva.di

import com.example.lesson_3_zhuravleva.presentation.ui.catalog.CatalogAdapter
import com.example.lesson_3_zhuravleva.presentation.ui.catalog.CatalogFragment
import com.example.lesson_3_zhuravleva.presentation.ui.product.ImagesAdapter
import com.example.lesson_3_zhuravleva.presentation.ui.product.ProductFragment
import com.example.lesson_3_zhuravleva.presentation.ui.product.size.SizeAdapter
import com.example.lesson_3_zhuravleva.presentation.ui.product.size.SizesBottomSheetFragment
import dagger.Module
import dagger.Provides

@Module
class ListenerModule {

    @Provides
    fun provideCatalogListener(fragment: CatalogFragment): CatalogAdapter.Listener{
        return fragment
    }

    @Provides
    fun provideImageListener(fragment: ProductFragment): ImagesAdapter.Listener{
        return fragment
    }

    @Provides
    fun provideSizeListener(fragment: SizesBottomSheetFragment): SizeAdapter.Listener{
        return fragment
    }
}