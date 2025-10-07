package com.saibabui.main.presentation.ui.details_gethering

import com.saibabui.network.home.model.*

/**
 * UI Models for Profile Form
 * These models are used internally in the UI layer to provide separation from API models
 */

data class ProfileFormUiModel(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val dateOfBirth: String? = null,
    val nationality: String? = null,
    val linkedinUrl: String? = null,
    val githubUrl: String? = null,
    val portfolioUrl: String? = null,
    val twitterUrl: String? = null,
    val customWebsite: String? = null,
    val professionalSummary: String? = null,
    val headline: String? = null,
    val avatarUrl: String? = null,
    val profileCompletionPercentage: Int = 0,
    val isActive: Boolean = true,
    val id: String = "",
    val userId: String = ""
)

data class ProfileFormCreateUiModel(
    val fullName: String,
    val email: String,
    val phone: String,
    val location: String,
    val dateOfBirth: String? = null,
    val nationality: String? = null,
    val linkedinUrl: String? = null,
    val githubUrl: String? = null,
    val portfolioUrl: String? = null,
    val twitterUrl: String? = null,
    val customWebsite: String? = null,
    val professionalSummary: String? = null,
    val headline: String? = null,
    val avatarUrl: String? = null,
    val userId: String
)

data class ProfileFormUpdateUiModel(
    val fullName: String? = null,
    val phone: String? = null,
    val location: String? = null,
    val dateOfBirth: String? = null,
    val nationality: String? = null,
    val linkedinUrl: String? = null,
    val githubUrl: String? = null,
    val portfolioUrl: String? = null,
    val twitterUrl: String? = null,
    val customWebsite: String? = null,
    val professionalSummary: String? = null,
    val headline: String? = null,
    val avatarUrl: String? = null
)

/**
 * Mapper extensions to convert between API models and UI models
 */
object ProfileFormMappers {
    // Convert API model to UI model
    fun UserProfileDetails.toUiModel(): ProfileFormUiModel {
        return ProfileFormUiModel(
            fullName = this.fullName,
            email = this.email,
            phone = this.phone,
            location = this.location,
            dateOfBirth = this.dateOfBirth,
            nationality = this.nationality,
            linkedinUrl = this.linkedinUrl,
            githubUrl = this.githubUrl,
            portfolioUrl = this.portfolioUrl,
            twitterUrl = this.twitterUrl,
            customWebsite = this.customWebsite,
            professionalSummary = this.professionalSummary,
            headline = this.headline,
            avatarUrl = this.avatarUrl,
            profileCompletionPercentage = this.profileCompletionPercentage,
            isActive = this.isActive,
            id = this.id,
            userId = this.userId
        )
    }
    
    // Convert API model to UI model
    fun UserProfileResponse.toUiModel(): ProfileFormUiModel {
        // Assuming UserProfileResponse has similar fields to UserProfileDetails
        // If not, you'd map from the actual fields of UserProfileResponse
        return ProfileFormUiModel(
            fullName = this.fullName ?: "",
            email = this.email ?: "",
            phone = this.phone ?: "",
            location = this.location ?: "",
            dateOfBirth = this.dateOfBirth,
            nationality = this.nationality,
            linkedinUrl = this.linkedinUrl,
            githubUrl = this.githubUrl,
            portfolioUrl = this.portfolioUrl,
            twitterUrl = this.twitterUrl,
            customWebsite = this.customWebsite,
            professionalSummary = this.professionalSummary,
            headline = this.headline,
            avatarUrl = this.avatarUrl,
            id = this.id ?: "",
            userId = this.userId ?: ""
        )
    }
    
    // Convert UI model to API model
    fun ProfileFormCreateUiModel.toApiModel(): UserProfileCreate {
        return UserProfileCreate(
            fullName = this.fullName,
            email = this.email,
            phone = this.phone,
            location = this.location,
            dateOfBirth = this.dateOfBirth,
            nationality = this.nationality,
            linkedinUrl = this.linkedinUrl,
            githubUrl = this.githubUrl,
            portfolioUrl = this.portfolioUrl,
            twitterUrl = this.twitterUrl,
            customWebsite = this.customWebsite,
            professionalSummary = this.professionalSummary,
            headline = this.headline,
            avatarUrl = this.avatarUrl,
            userId = this.userId
        )
    }
    
    // Convert UI model to API model
    fun ProfileFormUpdateUiModel.toApiModel(): UserProfileUpdate {
        return UserProfileUpdate(
            fullName = this.fullName,
            phone = this.phone,
            location = this.location,
            dateOfBirth = this.dateOfBirth,
            nationality = this.nationality,
            linkedinUrl = this.linkedinUrl,
            githubUrl = this.githubUrl,
            portfolioUrl = this.portfolioUrl,
            twitterUrl = this.twitterUrl,
            customWebsite = this.customWebsite,
            professionalSummary = this.professionalSummary,
            headline = this.headline,
            avatarUrl = this.avatarUrl
        )
    }
    
    // Convert UI model to API model for creation
    fun ProfileFormUiModel.toCreateApiModel(): UserProfileCreate {
        return UserProfileCreate(
            fullName = this.fullName,
            email = this.email,
            phone = this.phone,
            location = this.location,
            dateOfBirth = this.dateOfBirth,
            nationality = this.nationality,
            linkedinUrl = this.linkedinUrl,
            githubUrl = this.githubUrl,
            portfolioUrl = this.portfolioUrl,
            twitterUrl = this.twitterUrl,
            customWebsite = this.customWebsite,
            professionalSummary = this.professionalSummary,
            headline = this.headline,
            avatarUrl = this.avatarUrl,
            userId = this.userId
        )
    }
    
    // Convert UI model to API model for update
    fun ProfileFormUiModel.toUpdateApiModel(): UserProfileUpdate {
        return UserProfileUpdate(
            fullName = this.fullName,
            phone = this.phone,
            location = this.location,
            dateOfBirth = this.dateOfBirth,
            nationality = this.nationality,
            linkedinUrl = this.linkedinUrl,
            githubUrl = this.githubUrl,
            portfolioUrl = this.portfolioUrl,
            twitterUrl = this.twitterUrl,
            customWebsite = this.customWebsite,
            professionalSummary = this.professionalSummary,
            headline = this.headline,
            avatarUrl = this.avatarUrl
        )
    }
    
    // Convert from creation model to UI model
    fun UserProfileCreate.toUiModel(): ProfileFormCreateUiModel {
        return ProfileFormCreateUiModel(
            fullName = this.fullName,
            email = this.email,
            phone = this.phone,
            location = this.location,
            dateOfBirth = this.dateOfBirth,
            nationality = this.nationality,
            linkedinUrl = this.linkedinUrl,
            githubUrl = this.githubUrl,
            portfolioUrl = this.portfolioUrl,
            twitterUrl = this.twitterUrl,
            customWebsite = this.customWebsite,
            professionalSummary = this.professionalSummary,
            headline = this.headline,
            avatarUrl = this.avatarUrl,
            userId = this.userId
        )
    }
    
    // Convert from update model to UI model
    fun UserProfileUpdate.toUiModel(): ProfileFormUpdateUiModel {
        return ProfileFormUpdateUiModel(
            fullName = this.fullName,
            phone = this.phone,
            location = this.location,
            dateOfBirth = this.dateOfBirth,
            nationality = this.nationality,
            linkedinUrl = this.linkedinUrl,
            githubUrl = this.githubUrl,
            portfolioUrl = this.portfolioUrl,
            twitterUrl = this.twitterUrl,
            customWebsite = this.customWebsite,
            professionalSummary = this.professionalSummary,
            headline = this.headline,
            avatarUrl = this.avatarUrl
        )
    }
}