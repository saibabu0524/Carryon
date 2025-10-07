package com.saibabui.network.home.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileEducationResponse(
    @SerialName("institution_name")
    val institutionName: String,
    @SerialName("degree")
    val degree: String,
    @SerialName("field_of_study")
    val fieldOfStudy: String,
    @SerialName("location")
    val location: String,
    @SerialName("start_date")
    val startDate: String, // Format: date
    @SerialName("end_date")
    val endDate: String? = null, // Format: date
    @SerialName("is_current")
    val isCurrent: Boolean = false,
    @SerialName("gpa")
    val gpa: Double? = null,
    @SerialName("gpa_scale")
    val gpaScale: Double = 4.0,
    @SerialName("honors")
    val honors: String? = null,
    @SerialName("relevant_coursework")
    val relevantCoursework: List<String>? = null,
    @SerialName("display_order")
    val displayOrder: Int = 0,
    @SerialName("id")
    val id: String,
    @SerialName("profile_id")
    val profileId: String
)

@Serializable
data class ProfileEducationCreate(
    @SerialName("institution_name")
    val institutionName: String,
    @SerialName("degree")
    val degree: String,
    @SerialName("field_of_study")
    val fieldOfStudy: String,
    @SerialName("location")
    val location: String,
    @SerialName("start_date")
    val startDate: String, // Format: date
    @SerialName("end_date")
    val endDate: String? = null, // Format: date
    @SerialName("is_current")
    val isCurrent: Boolean = false,
    @SerialName("gpa")
    val gpa: Double? = null,
    @SerialName("gpa_scale")
    val gpaScale: Double = 4.0,
    @SerialName("honors")
    val honors: String? = null,
    @SerialName("relevant_coursework")
    val relevantCoursework: List<String>? = null,
    @SerialName("display_order")
    val displayOrder: Int = 0
)

@Serializable
data class ProfileEducationUpdate(
    @SerialName("institution_name")
    val institutionName: String? = null,
    @SerialName("degree")
    val degree: String? = null,
    @SerialName("field_of_study")
    val fieldOfStudy: String? = null,
    @SerialName("location")
    val location: String? = null,
    @SerialName("start_date")
    val startDate: String? = null, // Format: date
    @SerialName("end_date")
    val endDate: String? = null, // Format: date
    @SerialName("is_current")
    val isCurrent: Boolean? = null,
    @SerialName("gpa")
    val gpa: Double? = null,
    @SerialName("gpa_scale")
    val gpaScale: Double? = null,
    @SerialName("honors")
    val honors: String? = null,
    @SerialName("relevant_coursework")
    val relevantCoursework: List<String>? = null,
    @SerialName("display_order")
    val displayOrder: Int? = null
)