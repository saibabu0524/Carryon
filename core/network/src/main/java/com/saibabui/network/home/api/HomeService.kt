package com.saibabui.network.home.api

import com.saibabui.network.home.model.*
import retrofit2.Response
import retrofit2.http.*

interface HomeService {
    // Profile endpoints
    @GET("api/v1/profile/")
    suspend fun getProfile(): Response<UserProfileResponse>

    @PUT("api/v1/profile/")
    suspend fun updateProfile(@Body profile: UserProfileUpdate): Response<UserProfileResponse>

    @POST("api/v1/profile/")
    suspend fun createProfile(@Body profile: UserProfileCreate): Response<UserProfileResponse>

    @DELETE("api/v1/profile/")
    suspend fun deleteProfile(): Response<Unit>

    @GET("api/v1/profile/completion")
    suspend fun getProfileCompletion(): Response<Map<String, Any>>

    // Added for compatibility with existing ViewModel
    @GET("api/v1/home/profile-details") // This matches what's mentioned in the README
    suspend fun getProfileDetails(): Response<UserProfileDetails>

    // Skills endpoints
    @GET("api/v1/skills/")
    suspend fun getSkills(): Response<List<ProfileSkillResponse>>

    @POST("api/v1/skills/")
    suspend fun addSkill(@Body skill: ProfileSkillCreate): Response<ProfileSkillResponse>

    @PUT("api/v1/skills/{skill_id}")
    suspend fun updateSkill(@Path("skill_id") skillId: String, @Body skill: ProfileSkillUpdate): Response<ProfileSkillResponse>

    @DELETE("api/v1/skills/{skill_id}")
    suspend fun deleteSkill(@Path("skill_id") skillId: String): Response<Unit>

    // Experience endpoints
    @GET("api/v1/experience/")
    suspend fun getExperience(): Response<List<ProfileExperienceResponse>>

    @POST("api/v1/experience/")
    suspend fun addExperience(@Body experience: ProfileExperienceCreate): Response<ProfileExperienceResponse>

    @PUT("api/v1/experience/{experience_id}")
    suspend fun updateExperience(@Path("experience_id") experienceId: String, @Body experience: ProfileExperienceUpdate): Response<ProfileExperienceResponse>

    @DELETE("api/v1/experience/{experience_id}")
    suspend fun deleteExperience(@Path("experience_id") experienceId: String): Response<Unit>

    // Education endpoints
    @GET("api/v1/education/")
    suspend fun getEducation(): Response<List<ProfileEducationResponse>>

    @POST("api/v1/education/")
    suspend fun addEducation(@Body education: ProfileEducationCreate): Response<ProfileEducationResponse>

    @PUT("api/v1/education/{education_id}")
    suspend fun updateEducation(@Path("education_id") educationId: String, @Body education: ProfileEducationUpdate): Response<ProfileEducationResponse>

    @DELETE("api/v1/education/{education_id}")
    suspend fun deleteEducation(@Path("education_id") educationId: String): Response<Unit>

    // Projects endpoints
    @GET("api/v1/projects/")
    suspend fun getProjects(): Response<List<ProfileProjectResponse>>

    @POST("api/v1/projects/")
    suspend fun addProject(@Body project: ProfileProjectCreate): Response<ProfileProjectResponse>

    @PUT("api/v1/projects/{project_id}")
    suspend fun updateProject(@Path("project_id") projectId: String, @Body project: ProfileProjectUpdate): Response<ProfileProjectResponse>

    @DELETE("api/v1/projects/{project_id}")
    suspend fun deleteProject(@Path("project_id") projectId: String): Response<Unit>

    // Certifications endpoints
    @GET("api/v1/certifications/")
    suspend fun getCertifications(): Response<List<ProfileCertificationResponse>>

    @POST("api/v1/certifications/")
    suspend fun addCertification(@Body certification: ProfileCertificationCreate): Response<ProfileCertificationResponse>

    @PUT("api/v1/certifications/{certification_id}")
    suspend fun updateCertification(@Path("certification_id") certificationId: String, @Body certification: ProfileCertificationUpdate): Response<ProfileCertificationResponse>

    @DELETE("api/v1/certifications/{certification_id}")
    suspend fun deleteCertification(@Path("certification_id") certificationId: String): Response<Unit>

    // Languages endpoints
    @GET("api/v1/languages/")
    suspend fun getLanguages(): Response<List<ProfileLanguageResponse>>

    @POST("api/v1/languages/")
    suspend fun addLanguage(@Body language: ProfileLanguageCreate): Response<ProfileLanguageResponse>

    @PUT("api/v1/languages/{language_id}")
    suspend fun updateLanguage(@Path("language_id") languageId: String, @Body language: ProfileLanguageUpdate): Response<ProfileLanguageResponse>

    @DELETE("api/v1/languages/{language_id}")
    suspend fun deleteLanguage(@Path("language_id") languageId: String): Response<Unit>

    // Analytics endpoints
    @GET("api/v1/analytics/profile/completion")
    suspend fun getProfileCompletionAnalytics(): Response<Map<String, Any>>

    @GET("api/v1/analytics/resumes/stats")
    suspend fun getResumeStats(): Response<Map<String, Any>>
}