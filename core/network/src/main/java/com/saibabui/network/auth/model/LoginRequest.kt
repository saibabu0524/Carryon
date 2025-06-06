package com.saibabui.network.auth.model

data class LoginRequest(
    val email: String? = null,
    val password: String? = null
)


data class SignUpRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null
)