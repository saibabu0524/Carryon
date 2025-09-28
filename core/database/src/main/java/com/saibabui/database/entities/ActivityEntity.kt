package com.saibabui.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val resumeId: Int?,
    val action: String,
    val details: String?,
    val createdAt: Long = System.currentTimeMillis()
)