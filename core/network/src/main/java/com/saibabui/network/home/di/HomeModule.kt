package com.saibabui.network.home.di


import com.saibabui.network.home.api.HomeService
import com.saibabui.network.home.repositories.HomeRepository
import com.saibabui.network.home.repositories.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(authService: HomeService): HomeRepository {
        return HomeRepositoryImpl(authService)
    }
}