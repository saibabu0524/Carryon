package com.saibabui.network.home.repositories

import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.ResponseBody

interface HomeRepository {
    // Profile Management
    suspend fun getProfileDetails(): Flow<ApiResponse<ProfileResponse>>
    suspend fun updateProfile(request: ProfileUpdateRequest): Flow<ApiResponse<ProfileResponse>>
    suspend fun uploadAvatar(file: MultipartBody.Part): Flow<ApiResponse<Map<String, Any>>>
    suspend fun deleteAvatar(): Flow<ApiResponse<Map<String, Any>>>
    
    // Resume Management
    suspend fun getUserResumes(page: Int, limit: Int): Flow<ApiResponse<List<ResumeResponse>>>
    suspend fun createResume(resumeTitle: String, templateId: String?, aiGenerate: Boolean): Flow<ApiResponse<ResumeResponse>>
    suspend fun updateResume(resumeId: Int, resumeTitle: String?, templateId: String?, content: String?): Flow<ApiResponse<ResumeResponse>>
    suspend fun deleteResume(resumeId: Int): Flow<ApiResponse<Map<String, Any>>>
    suspend fun downloadResume(resumeId: Int): Flow<ApiResponse<Map<String, Any>>>
    
    // Search & Filter
    suspend fun searchResumes(query: String, page: Int, limit: Int): Flow<ApiResponse<List<ResumeResponse>>>
    suspend fun filterResumes(filters: Map<String, String>, page: Int, limit: Int): Flow<ApiResponse<List<ResumeResponse>>>
    suspend fun generateResumeFromTemplate(templateId: String, title: String, userData: String?): Flow<ApiResponse<ResumeResponse>>
    
    // Activity & Analytics
    suspend fun getActivityHistory(page: Int, limit: Int, actionType: String?): Flow<ApiResponse<List<ActivityResponse>>>
    suspend fun getResumeAnalytics(resumeId: Int): Flow<ApiResponse<Map<String, Any>>>
    suspend fun getDashboardStats(): Flow<ApiResponse<Map<String, Any>>>
    
    // Notifications
    suspend fun getNotifications(page: Int, limit: Int, isRead: Boolean?, notificationType: String?): Flow<ApiResponse<List<NotificationResponse>>>
    suspend fun markNotificationAsRead(notificationId: Int): Flow<ApiResponse<Map<String, Any>>>
    suspend fun markAllNotificationsAsRead(): Flow<ApiResponse<Map<String, Any>>>
    suspend fun deleteNotification(notificationId: Int): Flow<ApiResponse<Map<String, Any>>>
    
    // Collaboration
    suspend fun addCollaborator(resumeId: Int, email: String, role: String): Flow<ApiResponse<Map<String, Any>>>
    suspend fun getCollaborators(resumeId: Int): Flow<ApiResponse<List<Map<String, Any>>>>  
    suspend fun removeCollaborator(resumeId: Int, userId: Int): Flow<ApiResponse<Map<String, Any>>>
}
