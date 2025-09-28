package com.saibabui.network.home.api

import com.saibabui.network.home.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface HomeService {
    // Profile Management Endpoints
    @GET("/api/home/profile-details")
    suspend fun getProfileDetails(): Response<SuccessResponse<ProfileResponse>>

    @PUT("/api/home/profile-details")
    suspend fun updateProfile(@Body request: ProfileUpdateRequest): Response<SuccessResponse<ProfileResponse>>

    @Multipart
    @POST("/api/home/profile/upload-avatar")
    suspend fun uploadAvatar(@Part file: MultipartBody.Part): Response<SuccessResponse<Map<String, Any>>>

    @DELETE("/api/home/profile/avatar")
    suspend fun deleteAvatar(): Response<SuccessResponse<Map<String, Any>>>

    // Resume Management Endpoints
    @GET("/api/home/user-resumes")
    suspend fun getUserResumes(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<SuccessResponse<List<ResumeResponse>>>

    @POST("/api/home/resume/create")
    suspend fun createResume(
        @Query("resume_title") resumeTitle: String,
        @Query("template_id") templateId: String? = null,
        @Query("ai_generate") aiGenerate: Boolean = false
    ): Response<SuccessResponse<ResumeResponse>>

    @PUT("/api/home/resume/{resume_id}")
    suspend fun updateResume(
        @Path("resume_id") resumeId: Int,
        @Query("resume_title") resumeTitle: String? = null,
        @Query("template_id") templateId: String? = null,
        @Query("content") content: String? = null
    ): Response<SuccessResponse<ResumeResponse>>

    @DELETE("/api/home/resume/{resume_id}")
    suspend fun deleteResume(
        @Path("resume_id") resumeId: Int
    ): Response<SuccessResponse<Map<String, Any>>>

    @GET("/api/home/resume/{resume_id}/download")
    suspend fun downloadResume(
        @Path("resume_id") resumeId: Int
    ): Response<SuccessResponse<Map<String, Any>>>

    // Search and Filter Endpoints
    @GET("/api/home/resumes/search")
    suspend fun searchResumes(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<SuccessResponse<List<ResumeResponse>>>

    @GET("/api/home/resumes/filter")
    suspend fun filterResumes(
        @Query("category") category: String? = null,
        @Query("date_from") dateFrom: String? = null,
        @Query("date_to") dateTo: String? = null,
        @Query("is_public") isPublic: Boolean? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<SuccessResponse<List<ResumeResponse>>>

    @POST("/api/home/resume/generate-from-template")
    suspend fun generateResumeFromTemplate(
        @Query("template_id") templateId: String,
        @Query("resume_title") resumeTitle: String,
        @Query("user_data") userData: String? = null
    ): Response<SuccessResponse<ResumeResponse>>

    // Activity & Analytics Endpoints
    @GET("/api/home/activity-history")
    suspend fun getActivityHistory(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("action_type") actionType: String? = null
    ): Response<SuccessResponse<List<ActivityResponse>>>

    @GET("/api/home/resume-analytics/{resume_id}")
    suspend fun getResumeAnalytics(
        @Path("resume_id") resumeId: Int
    ): Response<SuccessResponse<Map<String, Any>>>

    @GET("/api/home/dashboard-stats")
    suspend fun getDashboardStats(): Response<SuccessResponse<Map<String, Any>>>

    // Notification Endpoints
    @GET("/api/home/notifications")
    suspend fun getNotifications(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("is_read") isRead: Boolean? = null,
        @Query("notification_type") notificationType: String? = null
    ): Response<SuccessResponse<List<NotificationResponse>>>

    @PUT("/api/home/notifications/mark-read/{notification_id}")
    suspend fun markNotificationAsRead(
        @Path("notification_id") notificationId: Int
    ): Response<SuccessResponse<Map<String, Any>>>

    @PUT("/api/home/notifications/mark-all-read")
    suspend fun markAllNotificationsAsRead(): Response<SuccessResponse<Map<String, Any>>>

    @DELETE("/api/home/notifications/{notification_id}")
    suspend fun deleteNotification(
        @Path("notification_id") notificationId: Int
    ): Response<SuccessResponse<Map<String, Any>>>

    // Collaboration Endpoints
    @POST("/api/home/resume/{resume_id}/collaborators")
    suspend fun addCollaborator(
        @Path("resume_id") resumeId: Int,
        @Query("email") email: String,
        @Query("role") role: String = "viewer"
    ): Response<SuccessResponse<Map<String, Any>>>

    @GET("/api/home/resume/{resume_id}/collaborators")
    suspend fun getCollaborators(
        @Path("resume_id") resumeId: Int
    ): Response<SuccessResponse<List<Map<String, Any>>>>

    @DELETE("/api/home/resume/{resume_id}/collaborators/{user_id}")
    suspend fun removeCollaborator(
        @Path("resume_id") resumeId: Int,
        @Path("user_id") userId: Int
    ): Response<SuccessResponse<Map<String, Any>>>

    // Template Management Endpoints
    @GET("/api/home/templates")
    suspend fun getTemplates(): Response<SuccessResponse<List<Map<String, Any>>>>

    @GET("/api/home/templates/my-templates")
    suspend fun getMyTemplates(): Response<SuccessResponse<List<Map<String, Any>>>>

    @Multipart
    @POST("/api/home/templates/custom")
    suspend fun createCustomTemplate(
        @Query("template_name") templateName: String,
        @Query("template_description") templateDescription: String,
        @Query("category") category: String? = null,
        @Part file: MultipartBody.Part
    ): Response<SuccessResponse<Map<String, Any>>>
}