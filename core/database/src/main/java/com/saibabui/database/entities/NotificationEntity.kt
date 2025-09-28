package com.saibabui.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val resumeId: Int?,
    val title: String,
    val message: String,
    val notificationType: String,
    val isRead: Boolean,
    val createdAt: Long = System.currentTimeMillis()
)