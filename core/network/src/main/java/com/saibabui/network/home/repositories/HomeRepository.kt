package com.saibabui.network.home.repositories

import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.*
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    // Profile endpoints
    suspend fun getProfile(): Flow<ApiResponse<UserProfileResponse>>
    suspend fun updateProfile(profile: UserProfileUpdate): Flow<ApiResponse<UserProfileResponse>>
    suspend fun createProfile(profile: UserProfileCreate): Flow<ApiResponse<UserProfileResponse>>
    suspend fun deleteProfile(): Flow<ApiResponse<Unit>>
    suspend fun getProfileCompletion(): Flow<ApiResponse<Map<String, Any>>>
    
    // Added for compatibility with existing ViewModel
    suspend fun getProfileDetails(): Flow<ApiResponse<UserProfileDetails>>

    // Skills endpoints
    suspend fun getSkills(): Flow<ApiResponse<List<ProfileSkillResponse>>>
    suspend fun addSkill(skill: ProfileSkillCreate): Flow<ApiResponse<ProfileSkillResponse>>
    suspend fun updateSkill(skillId: String, skill: ProfileSkillUpdate): Flow<ApiResponse<ProfileSkillResponse>>
    suspend fun deleteSkill(skillId: String): Flow<ApiResponse<Unit>>

    // Experience endpoints
    suspend fun getExperience(): Flow<ApiResponse<List<ProfileExperienceResponse>>>
    suspend fun addExperience(experience: ProfileExperienceCreate): Flow<ApiResponse<ProfileExperienceResponse>>
    suspend fun updateExperience(experienceId: String, experience: ProfileExperienceUpdate): Flow<ApiResponse<ProfileExperienceResponse>>
    suspend fun deleteExperience(experienceId: String): Flow<ApiResponse<Unit>>

    // Education endpoints
    suspend fun getEducation(): Flow<ApiResponse<List<ProfileEducationResponse>>>
    suspend fun addEducation(education: ProfileEducationCreate): Flow<ApiResponse<ProfileEducationResponse>>
    suspend fun updateEducation(educationId: String, education: ProfileEducationUpdate): Flow<ApiResponse<ProfileEducationResponse>>
    suspend fun deleteEducation(educationId: String): Flow<ApiResponse<Unit>>

    // Projects endpoints
    suspend fun getProjects(): Flow<ApiResponse<List<ProfileProjectResponse>>>
    suspend fun addProject(project: ProfileProjectCreate): Flow<ApiResponse<ProfileProjectResponse>>
    suspend fun updateProject(projectId: String, project: ProfileProjectUpdate): Flow<ApiResponse<ProfileProjectResponse>>
    suspend fun deleteProject(projectId: String): Flow<ApiResponse<Unit>>

    // Certifications endpoints
    suspend fun getCertifications(): Flow<ApiResponse<List<ProfileCertificationResponse>>>
    suspend fun addCertification(certification: ProfileCertificationCreate): Flow<ApiResponse<ProfileCertificationResponse>>
    suspend fun updateCertification(certificationId: String, certification: ProfileCertificationUpdate): Flow<ApiResponse<ProfileCertificationResponse>>
    suspend fun deleteCertification(certificationId: String): Flow<ApiResponse<Unit>>

    // Languages endpoints
    suspend fun getLanguages(): Flow<ApiResponse<List<ProfileLanguageResponse>>>
    suspend fun addLanguage(language: ProfileLanguageCreate): Flow<ApiResponse<ProfileLanguageResponse>>
    suspend fun updateLanguage(languageId: String, language: ProfileLanguageUpdate): Flow<ApiResponse<ProfileLanguageResponse>>
    suspend fun deleteLanguage(languageId: String): Flow<ApiResponse<Unit>>

    // Analytics endpoints
    suspend fun getProfileCompletionAnalytics(): Flow<ApiResponse<Map<String, Any>>>
    suspend fun getResumeStats(): Flow<ApiResponse<Map<String, Any>>>
}