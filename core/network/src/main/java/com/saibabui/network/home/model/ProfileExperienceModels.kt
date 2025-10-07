package com.saibabui.network.home.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileExperienceResponse(
    @SerialName("company_name")
    val companyName: String,
    @SerialName("position_title")
    val positionTitle: String,
    @SerialName("employment_type")
    val employmentType: String? = null,
    @SerialName("location")
    val location: String,
    @SerialName("start_date")
    val startDate: String, // Format: date
    @SerialName("end_date")
    val endDate: String? = null, // Format: date
    @SerialName("is_current")
    val isCurrent: Boolean = false,
    @SerialName("description")
    val description: String? = null,
    @SerialName("achievements")
    val achievements: List<String>? = null,
    @SerialName("display_order")
    val displayOrder: Int = 0,
    @SerialName("id")
    val id: String,
    @SerialName("profile_id")
    val profileId: String
)

@Serializable
data class ProfileExperienceCreate(
    @SerialName("company_name")
    val companyName: String,
    @SerialName("position_title")
    val positionTitle: String,
    @SerialName("employment_type")
    val employmentType: String? = null,
    @SerialName("location")
    val location: String,
    @SerialName("start_date")
    val startDate: String, // Format: date
    @SerialName("end_date")
    val endDate: String? = null, // Format: date
    @SerialName("is_current")
    val isCurrent: Boolean = false,
    @SerialName("description")
    val description: String? = null,
    @SerialName("achievements")
    val achievements: List<String>? = null,
    @SerialName("display_order")
    val displayOrder: Int = 0
)

@Serializable
data class ProfileExperienceUpdate(
    @SerialName("company_name")
    val companyName: String? = null,
    @SerialName("position_title")
    val positionTitle: String? = null,
    @SerialName("employment_type")
    val employmentType: String? = null,
    @SerialName("location")
    val location: String? = null,
    @SerialName("start_date")
    val startDate: String? = null, // Format: date
    @SerialName("end_date")
    val endDate: String? = null, // Format: date
    @SerialName("is_current")
    val isCurrent: Boolean? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("achievements")
    val achievements: List<String>? = null,
    @SerialName("display_order")
    val displayOrder: Int? = null
)