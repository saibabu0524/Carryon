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
    
    // Template Management - Updated according to API spec
    suspend fun getTemplates(categoryId: Int?, page: Int, pageSize: Int): Flow<ApiResponse<List<ResumeTemplateResponse>>>
    suspend fun getMyTemplates(page: Int, pageSize: Int): Flow<ApiResponse<List<ResumeTemplateResponse>>>
    suspend fun createCustomTemplate(
        file: MultipartBody.Part
    ): Flow<ApiResponse<Map<String, Any>>>
    
    // Category Management - Added according to API spec
    suspend fun getCategories(): Flow<ApiResponse<List<CategoryResponse>>>
    suspend fun createCategory(categoryCreate: CategoryCreate): Flow<ApiResponse<CategoryResponse>>
    suspend fun getCategory(categoryId: Int): Flow<ApiResponse<CategoryResponse>>
    suspend fun updateCategory(categoryId: Int, categoryCreate: CategoryCreate): Flow<ApiResponse<CategoryResponse>>
    suspend fun deleteCategory(categoryId: Int): Flow<ApiResponse<Map<String, Any>>>
    suspend fun getTemplatesByCategory(categoryId: Int, page: Int, pageSize: Int): Flow<ApiResponse<List<ResumeTemplateResponse>>>
    
    // Activity & Analytics
    suspend fun getActivityHistory(page: Int, limit: Int, actionType: String?): Flow<ApiResponse<List<ActivityResponse>>>
    suspend fun getResumeAnalytics(resumeId: Int): Flow<ApiResponse<Map<String, Any>>>
    suspend fun getDashboardStats(): Flow<ApiResponse<Map<String, Any>>>
    
    // Notifications
    suspend fun getNotifications(page: Int, limit: Int, isRead: Boolean?, notificationType: String?): Flow<ApiResponse<List<NotificationResponse>>>
    suspend fun markNotificationAsRead(notificationId: Int): Flow<ApiResponse<Map<String, Any>>>
    suspend fun markAllNotificationsAsRead(): Flow<ApiResponse<Map<String, Any>>>
    suspend fun deleteNotification(notificationId: Int): Flow<ApiResponse<Map<String, Any>>>
}
