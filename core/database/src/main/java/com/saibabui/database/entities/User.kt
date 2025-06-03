package com.saibabui.database.entities

import androidx.room.*

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "address") val address: String?
)

@Entity(
    tableName = "education",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["uid"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Education(
    @PrimaryKey(autoGenerate = true) val educationId: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "degree") val degree: String?,
    @ColumnInfo(name = "institution") val institution: String?,
    @ColumnInfo(name = "start_year") val startYear: String?,
    @ColumnInfo(name = "end_year") val endYear: String?
)

@Entity(
    tableName = "experience",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["uid"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Experience(
    @PrimaryKey(autoGenerate = true) val experienceId: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "job_title") val jobTitle: String?,
    @ColumnInfo(name = "company") val company: String?,
    @ColumnInfo(name = "start_date") val startDate: String?,
    @ColumnInfo(name = "end_date") val endDate: String?,
    @ColumnInfo(name = "description") val description: String?
)

@Entity(
    tableName = "skill",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["uid"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Skill(
    @PrimaryKey(autoGenerate = true) val skillId: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "skill_name") val skillName: String?,
    @ColumnInfo(name = "proficiency") val proficiency: String?
)