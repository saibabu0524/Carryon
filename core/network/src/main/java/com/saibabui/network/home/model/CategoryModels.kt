package com.saibabui.network.home.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    val id: Int,
    val name: String,
    val description: String? = null,
    @SerializedName("created_at")
    val createdAt: String, // ISO date string from API
    @SerializedName("updated_at")
    val updatedAt: String // ISO date string from API
)

data class CategoryCreate(
    val name: String,
    val description: String? = null
)