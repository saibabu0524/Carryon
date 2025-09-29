package com.saibabui.network.utils

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://3bb5bd740e05.ngrok-free.app/"

    fun createRetrofit(context: Context, isDebug: Boolean, useMock: Boolean): Retrofit {
        // Initialize TokenManager with context
        TokenManager.initialize(context)
        
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val tokenAuthenticator = TokenAuthenticator()

        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // connection timeout
            .readTimeout(30, TimeUnit.SECONDS)    // socket read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // socket write timeout
            .authenticator(TokenAuthenticatorWithRefresh()) // Add authenticator for 401 responses

        if (useMock) {
            builder.addInterceptor(MockInterceptor(context))
        }
        builder.addInterceptor(tokenAuthenticator)
        builder.addInterceptor(loggingInterceptor)

        val okHttpClient = builder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
