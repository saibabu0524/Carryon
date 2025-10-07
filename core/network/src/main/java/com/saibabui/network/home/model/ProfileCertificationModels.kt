package com.saibabui.network.home.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ProfileCertificationResponse(
    @SerialName("certification_name")
    val certificationName: String,
    @SerialName("issuing_organization")
    val issuingOrganization: String,
    @SerialName("issue_date")
    val issueDate: String, // Format: date
    @SerialName("expiry_date")
    val expiryDate: String? = null, // Format: date
    @SerialName("credential_id")
    val credentialId: String? = null,
    @SerialName("credential_url")
    val credentialUrl: String? = null,
    @SerialName("display_order")
    val displayOrder: Int = 0,
    @SerialName("id")
    val id: String,
    @SerialName("profile_id")
    val profileId: String
)

@kotlinx.serialization.Serializable
data class ProfileCertificationCreate(
    @SerialName("certification_name")
    val certificationName: String,
    @SerialName("issuing_organization")
    val issuingOrganization: String,
    @SerialName("issue_date")
    val issueDate: String, // Format: date
    @SerialName("expiry_date")
    val expiryDate: String? = null, // Format: date
    @SerialName("credential_id")
    val credentialId: String? = null,
    @SerialName("credential_url")
    val credentialUrl: String? = null,
    @SerialName("display_order")
    val displayOrder: Int = 0
)

@kotlinx.serialization.Serializable
data class ProfileCertificationUpdate(
    @SerialName("certification_name")
    val certificationName: String? = null,
    @SerialName("issuing_organization")
    val issuingOrganization: String? = null,
    @SerialName("issue_date")
    val issueDate: String? = null, // Format: date
    @SerialName("expiry_date")
    val expiryDate: String? = null, // Format: date
    @SerialName("credential_id")
    val credentialId: String? = null,
    @SerialName("credential_url")
    val credentialUrl: String? = null,
    @SerialName("display_order")
    val displayOrder: Int? = null
)