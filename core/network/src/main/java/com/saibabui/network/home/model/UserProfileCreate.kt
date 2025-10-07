package com.saibabui.network.home.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileCreate(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("email")
    val email: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("location")
    val location: String,
    @SerialName("date_of_birth")
    val dateOfBirth: String? = null,
    @SerialName("nationality")
    val nationality: String? = null,
    @SerialName("linkedin_url")
    val linkedinUrl: String? = null,
    @SerialName("github_url")
    val githubUrl: String? = null,
    @SerialName("portfolio_url")
    val portfolioUrl: String? = null,
    @SerialName("twitter_url")
    val twitterUrl: String? = null,
    @SerialName("custom_website")
    val customWebsite: String? = null,
    @SerialName("professional_summary")
    val professionalSummary: String? = null,
    @SerialName("headline")
    val headline: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("user_id")
    val userId: String
)