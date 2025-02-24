package com.saibabui.network.auth.di


import com.saibabui.network.auth.api.AuthService
import com.saibabui.network.auth.clint.RetrofitClient
import com.saibabui.network.auth.repositories.AuthRepository
import com.saibabui.network.auth.repositories.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a Retrofit instance using our dedicated client.
     * Here we use the module's BuildConfig flag to decide on logging.
     * If this is an Android library module, make sure to import its BuildConfig.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        // Import the correct BuildConfig for this network module.
        return RetrofitClient.createRetrofit(true)
    }

    /**
     * Provides an implementation of AuthService.
     */
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService)
    }
}
