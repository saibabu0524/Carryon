package com.saibabui.network.home.repositories

import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.utils.BaseRepository
import com.saibabui.network.home.api.HomeService
import com.saibabui.network.home.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.IOException
import org.json.JSONException
import org.json.JSONObject

class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository, BaseRepository() {
    
    // Custom apiCall for handling SuccessResponse wrapper
    private fun <T> apiCallWithSuccessResponse(apiCall: suspend () -> Response<SuccessResponse<T>>): Flow<ApiResponse<T>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val successResponse = response.body()
                if (successResponse != null && successResponse.data != null) {
                    emit(ApiResponse.Success(successResponse.data))
                } else {
                    emit(ApiResponse.Error("Empty response data"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = if (errorBody.isNullOrEmpty()) {
                    response.message()
                } else {
                    try {
                        JSONObject(errorBody).getString("message")
                    } catch (e: JSONException) {
                        response.message()
                    }
                }
                emit(ApiResponse.Error(errorMessage ?: "Unknown error", response.code()))
            }
        } catch (e: IOException) {
            emit(ApiResponse.Error("Network error, please try again"))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }

    override suspend fun getProfileDetails(): Flow<ApiResponse<ProfileResponse>> {
        return apiCallWithSuccessResponse { 
            homeService.getProfileDetails()
        }
    }

    override suspend fun updateProfile(request: ProfileUpdateRequest): Flow<ApiResponse<ProfileResponse>> {
        return apiCallWithSuccessResponse {
            homeService.updateProfile(request)
        }
    }

    override suspend fun uploadAvatar(file: MultipartBody.Part): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.uploadAvatar(file)
        }
    }

    override suspend fun deleteAvatar(): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.deleteAvatar()
        }
    }

    override suspend fun getUserResumes(page: Int, limit: Int): Flow<ApiResponse<List<ResumeResponse>>> {
        return apiCallWithSuccessResponse {
            homeService.getUserResumes(page, limit)
        }
    }

    override suspend fun createResume(resumeTitle: String, templateId: String?, aiGenerate: Boolean): Flow<ApiResponse<ResumeResponse>> {
        return apiCallWithSuccessResponse {
            homeService.createResume(resumeTitle, templateId, aiGenerate)
        }
    }

    override suspend fun updateResume(resumeId: Int, resumeTitle: String?, templateId: String?, content: String?): Flow<ApiResponse<ResumeResponse>> {
        return apiCallWithSuccessResponse {
            homeService.updateResume(resumeId, resumeTitle, templateId, content)
        }
    }

    override suspend fun deleteResume(resumeId: Int): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.deleteResume(resumeId)
        }
    }

    override suspend fun downloadResume(resumeId: Int): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.downloadResume(resumeId)
        }
    }

    override suspend fun searchResumes(query: String, page: Int, limit: Int): Flow<ApiResponse<List<ResumeResponse>>> {
        return apiCallWithSuccessResponse {
            homeService.searchResumes(query, page, limit)
        }
    }

    override suspend fun filterResumes(filters: Map<String, String>, page: Int, limit: Int): Flow<ApiResponse<List<ResumeResponse>>> {
        val category = filters["category"]
        val dateFrom = filters["date_from"]
        val dateTo = filters["date_to"]
        val isPublic = filters["is_public"]?.toBoolean()
        return apiCallWithSuccessResponse {
            homeService.filterResumes(category, dateFrom, dateTo, isPublic, page, limit)
        }
    }

    override suspend fun generateResumeFromTemplate(templateId: String, title: String, userData: String?): Flow<ApiResponse<ResumeResponse>> {
        return apiCallWithSuccessResponse {
            homeService.generateResumeFromTemplate(templateId, title, userData)
        }
    }

    override suspend fun getTemplates(categoryId: Int?, page: Int, pageSize: Int): Flow<ApiResponse<List<ResumeTemplateResponse>>> {
        return apiCallWithSuccessResponse {
            homeService.getTemplates(categoryId, page, pageSize)
        }
    }

    override suspend fun getMyTemplates(page: Int, pageSize: Int): Flow<ApiResponse<List<ResumeTemplateResponse>>> {
        return apiCallWithSuccessResponse {
            homeService.getMyTemplates(page, pageSize)
        }
    }

    override suspend fun createCustomTemplate(
        file: MultipartBody.Part
    ): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.createCustomTemplate(file)
        }
    }

    override suspend fun getCategories(): Flow<ApiResponse<List<CategoryResponse>>> {
        return apiCallWithSuccessResponse {
            homeService.getCategories()
        }
    }

    override suspend fun createCategory(categoryCreate: CategoryCreate): Flow<ApiResponse<CategoryResponse>> {
        return apiCallWithSuccessResponse {
            homeService.createCategory(categoryCreate)
        }
    }

    override suspend fun getCategory(categoryId: Int): Flow<ApiResponse<CategoryResponse>> {
        return apiCallWithSuccessResponse {
            homeService.getCategory(categoryId)
        }
    }

    override suspend fun updateCategory(categoryId: Int, categoryCreate: CategoryCreate): Flow<ApiResponse<CategoryResponse>> {
        return apiCallWithSuccessResponse {
            homeService.updateCategory(categoryId, categoryCreate)
        }
    }

    override suspend fun deleteCategory(categoryId: Int): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.deleteCategory(categoryId)
        }
    }

    override suspend fun getTemplatesByCategory(categoryId: Int, page: Int, pageSize: Int): Flow<ApiResponse<List<ResumeTemplateResponse>>> {
        return apiCallWithSuccessResponse {
            homeService.getTemplatesByCategory(categoryId, page, pageSize)
        }
    }

    override suspend fun getActivityHistory(page: Int, limit: Int, actionType: String?): Flow<ApiResponse<List<ActivityResponse>>> {
        return apiCallWithSuccessResponse {
            homeService.getActivityHistory(page, limit, actionType)
        }
    }

    override suspend fun getResumeAnalytics(resumeId: Int): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.getResumeAnalytics(resumeId)
        }
    }

    override suspend fun getDashboardStats(): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.getDashboardStats()
        }
    }

    override suspend fun getNotifications(page: Int, limit: Int, isRead: Boolean?, notificationType: String?): Flow<ApiResponse<List<NotificationResponse>>> {
        return apiCallWithSuccessResponse {
            homeService.getNotifications(page, limit, isRead, notificationType)
        }
    }

    override suspend fun markNotificationAsRead(notificationId: Int): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.markNotificationAsRead(notificationId)
        }
    }

    override suspend fun markAllNotificationsAsRead(): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.markAllNotificationsAsRead()
        }
    }

    override suspend fun deleteNotification(notificationId: Int): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse {
            homeService.deleteNotification(notificationId)
        }
    }
}
