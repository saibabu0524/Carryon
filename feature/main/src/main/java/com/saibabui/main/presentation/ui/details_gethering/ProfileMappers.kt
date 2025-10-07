package com.saibabui.main.presentation.ui.details_gethering

import com.saibabui.network.home.model.*

/**
 * Mapper extensions to convert API models to UI models and vice versa
 */
object ProfileMappers {
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
    
    fun UserProfileResponse.toUiModel(): ProfileFormUiModel {
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
    
    // From UI model to API models
    fun ProfileFormUiModel.toUserProfileCreate(userId: String): UserProfileCreate {
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
            userId = userId
        )
    }
    
    fun ProfileFormUiModel.toUserProfileUpdate(): UserProfileUpdate {
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
}