package com.saibabui.network.home.model

import com.google.gson.annotations.SerializedName

data class TemplateResponse(
    val id: Int,
    val name: String,
    val description: String?,
    val category: String?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("is__free")
    val isFree: Boolean,
    @SerializedName("is_popular")
    val isPopular: Boolean,
    @SerializedName("usage_count")
    val usageCount: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
