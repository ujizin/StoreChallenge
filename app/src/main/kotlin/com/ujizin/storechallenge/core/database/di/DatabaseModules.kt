package com.ujizin.storechallenge.core.database.di

import android.content.Context
import androidx.room.Room
import com.ujizin.storechallenge.core.database.ProductDatabase
import com.ujizin.storechallenge.core.database.ProductDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModules {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ) = Room.databaseBuilder(applicationContext, ProductDatabase::class.java, DATABASE_NAME).build()
}