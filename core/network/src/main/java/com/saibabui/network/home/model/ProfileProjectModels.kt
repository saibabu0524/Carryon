package com.saibabui.network.home.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileProjectResponse(
    @SerialName("project_name")
    val projectName: String,
    @SerialName("description")
    val description: String,
    @SerialName("role")
    val role: String? = null,
    @SerialName("start_date")
    val startDate: String? = null, // Format: date
    @SerialName("end_date")
    val endDate: String? = null, // Format: date
    @SerialName("is_ongoing")
    val isOngoing: Boolean = false,
    @SerialName("project_url")
    val projectUrl: String? = null,
    @SerialName("github_url")
    val githubUrl: String? = null,
    @SerialName("demo_url")
    val demoUrl: String? = null,
    @SerialName("technologies")
    val technologies: List<String>,
    @SerialName("highlights")
    val highlights: List<String>,
    @SerialName("display_order")
    val displayOrder: Int = 0,
    @SerialName("id")
    val id: String,
    @SerialName("profile_id")
    val profileId: String
)

@Serializable
data class ProfileProjectCreate(
    @SerialName("project_name")
    val projectName: String,
    @SerialName("description")
    val description: String,
    @SerialName("role")
    val role: String? = null,
    @SerialName("start_date")
    val startDate: String? = null, // Format: date
    @SerialName("end_date")
    val endDate: String? = null, // Format: date
    @SerialName("is_ongoing")
    val isOngoing: Boolean = false,
    @SerialName("project_url")
    val projectUrl: String? = null,
    @SerialName("github_url")
    val githubUrl: String? = null,
    @SerialName("demo_url")
    val demoUrl: String? = null,
    @SerialName("technologies")
    val technologies: List<String>,
    @SerialName("highlights")
    val highlights: List<String>,
    @SerialName("display_order")
    val displayOrder: Int = 0
)

@Serializable
data class ProfileProjectUpdate(
    @SerialName("project_name")
    val projectName: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("role")
    val role: String? = null,
    @SerialName("start_date")
    val startDate: String? = null, // Format: date
    @SerialName("end_date")
    val endDate: String? = null, // Format: date
    @SerialName("is_ongoing")
    val isOngoing: Boolean? = null,
    @SerialName("project_url")
    val projectUrl: String? = null,
    @SerialName("github_url")
    val githubUrl: String? = null,
    @SerialName("demo_url")
    val demoUrl: String? = null,
    @SerialName("technologies")
    val technologies: List<String>? = null,
    @SerialName("highlights")
    val highlights: List<String>? = null,
    @SerialName("display_order")
    val displayOrder: Int? = null
)