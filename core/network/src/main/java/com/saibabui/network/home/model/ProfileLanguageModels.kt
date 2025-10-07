package com.saibabui.network.home.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileLanguageResponse(
    @SerialName("language_name")
    val languageName: String,
    @SerialName("proficiency_level")
    val proficiencyLevel: String,
    @SerialName("display_order")
    val displayOrder: Int = 0,
    @SerialName("id")
    val id: String,
    @SerialName("profile_id")
    val profileId: String
)

@Serializable
data class ProfileLanguageCreate(
    @SerialName("language_name")
    val languageName: String,
    @SerialName("proficiency_level")
    val proficiencyLevel: String,
    @SerialName("display_order")
    val displayOrder: Int = 0
)

@Serializable
data class ProfileLanguageUpdate(
    @SerialName("language_name")
    val languageName: String? = null,
    @SerialName("proficiency_level")
    val proficiencyLevel: String? = null,
    @SerialName("display_order")
    val displayOrder: Int? = null
)