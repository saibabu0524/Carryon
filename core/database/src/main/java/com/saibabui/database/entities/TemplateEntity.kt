package com.saibabui.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.saibabui.database.utils.Converters

@Entity(tableName = "templates")
@TypeConverters(Converters::class)
data class TemplateEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val category: String,
    val previewImage: String,
    val description: String? = null,
    val tags: List<String>? = null,
    val usageCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isPopular: Boolean = false,
    val difficulty: String? = null,
    val estimatedTime: String? = null
)