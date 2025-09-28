package com.saibabui.network.home.model

import com.google.gson.annotations.SerializedName

data class Collaborator(
    @SerializedName("user_id")
    val userId: Int,
    val email: String,
    val role: String,
    @SerializedName("joined_at")
    val joinedAt: String // ISO date string from API
)