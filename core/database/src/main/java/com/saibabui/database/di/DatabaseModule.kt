package com.saibabui.database.di

import android.content.Context
import androidx.room.Room
import com.saibabui.database.AppDatabase
import com.saibabui.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "user-database"
    )
    .fallbackToDestructiveMigration() // For development - in production, use proper migrations
    .build()
}