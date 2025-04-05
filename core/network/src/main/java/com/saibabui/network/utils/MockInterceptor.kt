package com.saibabui.network.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val jsonResponse = getMockDataFromUrl(uri, context)
        println("mock response + $jsonResponse")
        return Response.Builder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message("Success")
            .request(chain.request())
            .body(ResponseBody.create("application/json".toMediaTypeOrNull(), jsonResponse))
            .build()
    }
}