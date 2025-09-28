package com.saibabui.database.di

import com.saibabui.database.AppDatabase
import com.saibabui.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseDaoModule {
    @Provides
    fun providesUserDao(
        database: AppDatabase,
    ): UserDao = database.userDao()

    @Provides
    fun provideResumeDao(database: AppDatabase): ResumeDao {
        return database.resumeDao()
    }

    @Provides
    fun provideTemplateDao(
        database: AppDatabase,
    ): TemplateDao = database.templateDao()

    @Provides
    fun provideProfileDao(
        database: AppDatabase,
    ): ProfileDao = database.profileDao()

    @Provides
    fun provideActivityDao(
        database: AppDatabase,
    ): ActivityDao = database.activityDao()

    @Provides
    fun provideNotificationDao(
        database: AppDatabase,
    ): NotificationDao = database.notificationDao()

    @Provides
    fun provideCollaboratorDao(
        database: AppDatabase,
    ): CollaboratorDao = database.collaboratorDao()

    @Provides
    fun provideBackendResumeDao(
        database: AppDatabase,
    ): BackendResumeDao = database.backendResumeDao()
}