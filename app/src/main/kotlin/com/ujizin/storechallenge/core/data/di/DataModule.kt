package com.ujizin.storechallenge.core.data.di

import com.ujizin.storechallenge.core.data.repository.ProductRepository
import com.ujizin.storechallenge.core.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
internal interface DataModule {

    @Binds
    fun provideProductRepository(
        productRepository: ProductRepositoryImpl
    ): ProductRepository
}
