package com.saibabui.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "backend_resumes")
data class BackendResumeEntity(
    @PrimaryKey
    val id: Int, // From backend API
    val userId: Int,
    val title: String,
    val templateId: String?,
    val content: String?,
    val fileUrl: String?,
    val isPublic: Boolean,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)