package com.saibabui.main.domain.di

import com.saibabui.main.domain.validation.profile.ProfileValidator
import com.saibabui.main.domain.validation.resume.ResumeValidator
import com.saibabui.main.domain.validation.template.TemplateValidator
import com.saibabui.main.domain.validation.notification.NotificationValidator
import com.saibabui.main.domain.validation.category.CategoryValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidationModule {

    @Provides
    @Singleton
    fun provideProfileValidator(): ProfileValidator = ProfileValidator()

    @Provides
    @Singleton
    fun provideResumeValidator(): ResumeValidator = ResumeValidator()

    @Provides
    @Singleton
    fun provideTemplateValidator(): TemplateValidator = TemplateValidator()

    @Provides
    @Singleton
    fun provideNotificationValidator(): NotificationValidator = NotificationValidator()

    @Provides
    @Singleton
    fun provideCategoryValidator(): CategoryValidator = CategoryValidator()
}