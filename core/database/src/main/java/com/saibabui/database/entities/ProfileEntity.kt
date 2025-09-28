package com.saibabui.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val address: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val postalCode: String?,
    val bio: String?,
    val avatarUrl: String?,
    val linkedinUrl: String?,
    val githubUrl: String?,
    val portfolioUrl: String?,
    val isPublic: Boolean,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)