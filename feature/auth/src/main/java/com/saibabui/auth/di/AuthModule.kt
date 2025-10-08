package com.saibabui.auth.di

import com.saibabui.auth.domain.usecases.ValidateConfirmPassword
import com.saibabui.auth.domain.usecases.ValidateEmail
import com.saibabui.auth.domain.usecases.ValidateMobileNumber
import com.saibabui.auth.domain.usecases.ValidateMobileOTP
import com.saibabui.auth.domain.usecases.ValidateName
import com.saibabui.auth.domain.usecases.ValidatePassword
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
    fun provideValidateEmail(): ValidateEmail {
        return ValidateEmail()
    }

    @Provides
    @Singleton
    fun provideValidateOTP(): ValidateMobileOTP {
        return ValidateMobileOTP()
    }

    @Provides
    @Singleton
    fun provideValidateConfirmPassword(): ValidateConfirmPassword {
        return ValidateConfirmPassword()
    }

    @Provides
    @Singleton
    fun provideValidatePassword(): ValidatePassword {
        return ValidatePassword()
    }

    @Provides
    @Singleton
    fun provideValidateName(): ValidateName {
        return ValidateName()
    }

    @Provides
    @Singleton
    fun provideValidateMobileNumber(): ValidateMobileNumber {
        return ValidateMobileNumber()
    }
}
