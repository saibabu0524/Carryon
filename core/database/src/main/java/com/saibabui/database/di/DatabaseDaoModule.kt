package com.saibabui.database.di

import com.saibabui.database.AppDatabase
import com.saibabui.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseDaoModule {
    @Provides
    fun providesTopicsDao(
        database: AppDatabase,
    ): UserDao = database.userDao()
}