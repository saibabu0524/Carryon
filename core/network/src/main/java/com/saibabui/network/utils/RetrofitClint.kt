package com.saibabui.network.utils

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://43709f529ecf.ngrok-free.app/"

    fun createRetrofit(context: Context, isDebug: Boolean, useMock: Boolean): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val builder = OkHttpClient.Builder()
        if (useMock) {
            builder.addInterceptor(MockInterceptor(context))
        }
        builder.addInterceptor(loggingInterceptor)

        val okHttpClient = builder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}