package com.saibabui.network.utils

import okhttp3.Interceptor
import okhttp3.Response

class TokenAuthenticator : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Get the stored token
        val token = TokenManager.getAccessToken()
        
        // Add the token to requests that require authentication
        // Typically all requests except auth endpoints need the token
        val authRequest = if (token != null && needsAuthentication(originalRequest.url.toString())) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }
        
        return chain.proceed(authRequest)
    }
    
    private fun needsAuthentication(url: String): Boolean {
        // Skip adding token for authentication endpoints (login, register, etc.)
        val authUrls = listOf(
            "/auth/login",
            "/auth/register", 
            "/auth/refresh",
            "/auth/logout",
            "/auth/forgot-password",
            "/auth/reset-password",
            "/auth/change-password",
            "/auth/google/login",
            "/auth/google/callback"
        )
        
        return authUrls.none { url.contains(it) }
    }
}