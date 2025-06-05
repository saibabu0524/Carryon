package com.saibabui.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resumes")
data class ResumeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val templateName: String,
    val templateId: String,
    val content: String? = null,
    val completionPercentage: Int = 0,
    val lastModified: Long,
    val createdAt: Long,
    val isCompleted: Boolean = false,
    val isFavorite: Boolean = false,
    val tags: String? = null, // JSON string for tags
    val exportedFormats: String? = null // JSON string for exported formats (PDF, DOCX)
)