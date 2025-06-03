package com.saibabui.main.di

import com.saibabui.database.dao.TemplateDao
import com.saibabui.main.data.TemplateApiService
import com.saibabui.main.data.TemplateApiServiceImpl
import com.saibabui.main.domain.TemplateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TemplateModule {

    @Provides
    @Singleton
    fun provideTemplateApiService() : TemplateApiService = TemplateApiServiceImpl()

    @Provides
    @Singleton
    fun provideTemplateRepository(
        templateApiService: TemplateApiService,
        templateDao: TemplateDao
    ): TemplateRepository = TemplateRepository(
        templateApiService = templateApiService,
        templateDao =  templateDao// Replace with actual DAO when available
    )

}