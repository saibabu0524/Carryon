package com.saibabui.auth.di

import com.saibabui.auth.domain.usecases.ValidateMobileNumber
import com.saibabui.auth.domain.usecases.ValidateMobileOTP
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Provides
    @Singleton
    fun provideValidateEmail(): ValidateMobileNumber {
        return ValidateMobileNumber()
    }
    @Provides
    @Singleton
    fun provideValidateOTP(): ValidateMobileOTP {
        return ValidateMobileOTP()
    }
}
