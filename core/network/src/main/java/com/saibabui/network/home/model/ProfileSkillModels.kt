package com.saibabui.network.home.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileSkillResponse(
    @SerialName("skill_name")
    val skillName: String,
    @SerialName("skill_type")
    val skillType: String,
    @SerialName("proficiency_level")
    val proficiencyLevel: String? = null,
    @SerialName("years_of_experience")
    val yearsOfExperience: Int? = null,
    @SerialName("display_order")
    val displayOrder: Int = 0,
    @SerialName("id")
    val id: String,
    @SerialName("profile_id")
    val profileId: String
)

@Serializable
data class ProfileSkillCreate(
    @SerialName("skill_name")
    val skillName: String,
    @SerialName("skill_type")
    val skillType: String,
    @SerialName("proficiency_level")
    val proficiencyLevel: String? = null,
    @SerialName("years_of_experience")
    val yearsOfExperience: Int? = null,
    @SerialName("display_order")
    val displayOrder: Int = 0
)

@Serializable
data class ProfileSkillUpdate(
    @SerialName("skill_name")
    val skillName: String? = null,
    @SerialName("skill_type")
    val skillType: String? = null,
    @SerialName("proficiency_level")
    val proficiencyLevel: String? = null,
    @SerialName("years_of_experience")
    val yearsOfExperience: Int? = null,
    @SerialName("display_order")
    val displayOrder: Int? = null
)