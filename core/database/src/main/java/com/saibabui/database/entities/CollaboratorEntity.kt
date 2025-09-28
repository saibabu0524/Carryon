package com.saibabui.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "collaborators")
data class CollaboratorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("resume_id")
    val resumeId: Int,
    @SerializedName("user_id")
    val userId: Int,
    val email: String,
    val role: String, // viewer, editor, admin
    @SerializedName("created_at")
    val createdAt: Long = System.currentTimeMillis()
)