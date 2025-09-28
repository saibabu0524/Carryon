package com.saibabui.network.home.model

import com.google.gson.annotations.SerializedName

data class ProfileUpdateRequest(
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    @SerializedName("postal_code")
    val postalCode: String? = null,
    val bio: String? = null,
    @SerializedName("linkedin_url")
    val linkedinUrl: String? = null,
    @SerializedName("github_url")
    val githubUrl: String? = null,
    @SerializedName("portfolio_url")
    val portfolioUrl: String? = null,
    @SerializedName("is_public")
    val isPublic: Boolean? = null
)

data class ProfileResponse(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    val phone: String?,
    val address: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    @SerializedName("postal_code")
    val postalCode: String?,
    val bio: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("linkedin_url")
    val linkedinUrl: String?,
    @SerializedName("github_url")
    val githubUrl: String?,
    @SerializedName("portfolio_url")
    val portfolioUrl: String?,
    @SerializedName("is_public")
    val isPublic: Boolean,
    @SerializedName("created_at")
    val createdAt: String, // ISO date string from API
    @SerializedName("updated_at")
    val updatedAt: String // ISO date string from API
)

data class ResumeResponse(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    val title: String,
    @SerializedName("template_id")
    val templateId: String?,
    val content: String?,
    @SerializedName("file_url")
    val fileUrl: String?,
    @SerializedName("is_public")
    val isPublic: Boolean,
    @SerializedName("created_at")
    val createdAt: String, // ISO date string from API
    @SerializedName("updated_at")
    val updatedAt: String // ISO date string from API
)

data class ActivityResponse(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("resume_id")
    val resumeId: Int?,
    val action: String,
    val details: String?,
    @SerializedName("created_at")
    val createdAt: String // ISO date string from API
)

data class NotificationResponse(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("resume_id")
    val resumeId: Int?,
    val title: String,
    val message: String,
    @SerializedName("notification_type")
    val notificationType: String,
    @SerializedName("is_read")
    val isRead: Boolean,
    @SerializedName("created_at")
    val createdAt: String // ISO date string from API
)

data class ChangePasswordRequest(
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("new_password")
    val newPassword: String
)

data class ForgotPasswordRequest(
    val email: String
)

data class ResetPasswordRequest(
    val token: String,
    @SerializedName("new_password")
    val newPassword: String
)

data class RefreshTokenRequest(
    @SerializedName("refresh_token")
    val refreshToken: String
)

data class UserCreate(
    val email: String,
    @SerializedName("full_name")
    val fullName: String? = null,
    val password: String
)

data class UserLogin(
    val email: String,
    val password: String
)