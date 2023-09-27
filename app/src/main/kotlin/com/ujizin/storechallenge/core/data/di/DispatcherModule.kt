package com.ujizin.storechallenge.core.data.di

import com.ujizin.storechallenge.core.data.dispatcher.Dispatcher
import com.ujizin.storechallenge.core.data.dispatcher.ProductDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object DispatcherModule {

    @Provides
    @Dispatcher(ProductDispatcher.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}
