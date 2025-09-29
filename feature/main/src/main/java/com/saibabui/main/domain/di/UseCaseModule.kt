package com.saibabui.main.domain.di

import com.saibabui.main.domain.usecases.category.*
import com.saibabui.main.domain.usecases.template.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(
        getCategoriesUseCase: GetCategoriesUseCase
    ) = getCategoriesUseCase

    @Provides
    @Singleton
    fun provideCreateCategoryUseCase(
        createCategoryUseCase: CreateCategoryUseCase
    ) = createCategoryUseCase

    @Provides
    @Singleton
    fun provideGetCategoryUseCase(
        getCategoryUseCase: GetCategoryUseCase
    ) = getCategoryUseCase

    @Provides
    @Singleton
    fun provideUpdateCategoryUseCase(
        updateCategoryUseCase: UpdateCategoryUseCase
    ) = updateCategoryUseCase

    @Provides
    @Singleton
    fun provideDeleteCategoryUseCase(
        deleteCategoryUseCase: DeleteCategoryUseCase
    ) = deleteCategoryUseCase

    @Provides
    @Singleton
    fun provideGetTemplatesByCategoryUseCase(
        getTemplatesByCategoryUseCase: GetTemplatesByCategoryUseCase
    ) = getTemplatesByCategoryUseCase

    @Provides
    @Singleton
    fun provideGetTemplatesUseCase(
        getTemplatesUseCase: GetTemplatesUseCase
    ) = getTemplatesUseCase

    @Provides
    @Singleton
    fun provideGetMyTemplatesUseCase(
        getMyTemplatesUseCase: GetMyTemplatesUseCase
    ) = getMyTemplatesUseCase

    @Provides
    @Singleton
    fun provideCreateCustomTemplateUseCase(
        createCustomTemplateUseCase: CreateCustomTemplateUseCase
    ) = createCustomTemplateUseCase
}