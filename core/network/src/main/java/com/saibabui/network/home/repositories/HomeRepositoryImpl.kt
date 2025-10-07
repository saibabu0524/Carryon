package com.saibabui.network.home.repositories

import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.api.HomeService
import com.saibabui.network.home.model.*
import com.saibabui.network.utils.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

//class HomeRepositoryImpl @Inject constructor(
//    private val homeService: HomeService
//) : HomeRepository {
//    override suspend fun getProfile(): Flow<ApiResponse<UserProfileResponse>> = flow {
//        try {
//            val response = homeService.getProfile()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error fetching profile")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun updateProfile(profile: UserProfileUpdate): Flow<ApiResponse<UserProfileResponse>> = flow {
//        try {
//            val response = homeService.updateProfile(profile)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error updating profile")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun createProfile(profile: UserProfileCreate): Flow<ApiResponse<UserProfileResponse>> = flow {
//        try {
//            val response = homeService.createProfile(profile)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error creating profile")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun deleteProfile(): Flow<ApiResponse<Unit>> = flow {
//        try {
//            val response = homeService.deleteProfile()
//            if (response.isSuccessful) {
//                emit(ApiResponse.Success(Unit))
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error deleting profile")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun getProfileCompletion(): Flow<ApiResponse<Map<String, Any>>> = flow {
//        try {
//            val response = homeService.getProfileCompletion()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error fetching profile completion")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    // Skills endpoints
//    override suspend fun getSkills(): Flow<ApiResponse<List<ProfileSkillResponse>>> = flow {
//        try {
//            val response = homeService.getSkills()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error fetching skills")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun addSkill(skill: ProfileSkillCreate): Flow<ApiResponse<ProfileSkillResponse>> = flow {
//        try {
//            val response = homeService.addSkill(skill)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error adding skill")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun updateSkill(skillId: String, skill: ProfileSkillUpdate): Flow<ApiResponse<ProfileSkillResponse>> = flow {
//        try {
//            val response = homeService.updateSkill(skillId, skill)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error updating skill")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun deleteSkill(skillId: String): Flow<ApiResponse<Unit>> = flow {
//        try {
//            val response = homeService.deleteSkill(skillId)
//            if (response.isSuccessful) {
//                emit(ApiResponse.Success(Unit))
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error deleting skill")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    // Experience endpoints
//    override suspend fun getExperience(): Flow<ApiResponse<List<ProfileExperienceResponse>>> = flow {
//        try {
//            val response = homeService.getExperience()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error fetching experience")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun addExperience(experience: ProfileExperienceCreate): Flow<ApiResponse<ProfileExperienceResponse>> = flow {
//        try {
//            val response = homeService.addExperience(experience)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error adding experience")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun updateExperience(experienceId: String, experience: ProfileExperienceUpdate): Flow<ApiResponse<ProfileExperienceResponse>> = flow {
//        try {
//            val response = homeService.updateExperience(experienceId, experience)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error updating experience")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun deleteExperience(experienceId: String): Flow<ApiResponse<Unit>> = flow {
//        try {
//            val response = homeService.deleteExperience(experienceId)
//            if (response.isSuccessful) {
//                emit(ApiResponse.Success(Unit))
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error deleting experience")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    // Education endpoints
//    override suspend fun getEducation(): Flow<ApiResponse<List<ProfileEducationResponse>>> = flow {
//        try {
//            val response = homeService.getEducation()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error fetching education")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun addEducation(education: ProfileEducationCreate): Flow<ApiResponse<ProfileEducationResponse>> = flow {
//        try {
//            val response = homeService.addEducation(education)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error adding education")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun updateEducation(educationId: String, education: ProfileEducationUpdate): Flow<ApiResponse<ProfileEducationResponse>> = flow {
//        try {
//            val response = homeService.updateEducation(educationId, education)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error updating education")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun deleteEducation(educationId: String): Flow<ApiResponse<Unit>> = flow {
//        try {
//            val response = homeService.deleteEducation(educationId)
//            if (response.isSuccessful) {
//                emit(ApiResponse.Success(Unit))
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error deleting education")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    // Projects endpoints
//    override suspend fun getProjects(): Flow<ApiResponse<List<ProfileProjectResponse>>> = flow {
//        try {
//            val response = homeService.getProjects()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error fetching projects")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun addProject(project: ProfileProjectCreate): Flow<ApiResponse<ProfileProjectResponse>> = flow {
//        try {
//            val response = homeService.addProject(project)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error adding project")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun updateProject(projectId: String, project: ProfileProjectUpdate): Flow<ApiResponse<ProfileProjectResponse>> = flow {
//        try {
//            val response = homeService.updateProject(projectId, project)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error updating project")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun deleteProject(projectId: String): Flow<ApiResponse<Unit>> = flow {
//        try {
//            val response = homeService.deleteProject(projectId)
//            if (response.isSuccessful) {
//                emit(ApiResponse.Success(Unit))
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error deleting project")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    // Certifications endpoints
//    override suspend fun getCertifications(): Flow<ApiResponse<List<ProfileCertificationResponse>>> = flow {
//        try {
//            val response = homeService.getCertifications()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error fetching certifications")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun addCertification(certification: ProfileCertificationCreate): Flow<ApiResponse<ProfileCertificationResponse>> = flow {
//        try {
//            val response = homeService.addCertification(certification)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error adding certification")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun updateCertification(certificationId: String, certification: ProfileCertificationUpdate): Flow<ApiResponse<ProfileCertificationResponse>> = flow {
//        try {
//            val response = homeService.updateCertification(certificationId, certification)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error updating certification")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun deleteCertification(certificationId: String): Flow<ApiResponse<Unit>> = flow {
//        try {
//            val response = homeService.deleteCertification(certificationId)
//            if (response.isSuccessful) {
//                emit(ApiResponse.Success(Unit))
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error deleting certification")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    // Languages endpoints
//    override suspend fun getLanguages(): Flow<ApiResponse<List<ProfileLanguageResponse>>> = flow {
//        try {
//            val response = homeService.getLanguages()
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error fetching languages")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun addLanguage(language: ProfileLanguageCreate): Flow<ApiResponse<ProfileLanguageResponse>> = flow {
//        try {
//            val response = homeService.addLanguage(language)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error adding language")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun updateLanguage(languageId: String, language: ProfileLanguageUpdate): Flow<ApiResponse<ProfileLanguageResponse>> = flow {
//        try {
//            val response = homeService.updateLanguage(languageId, language)
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(ApiResponse.Success(it))
//                } ?: run {
//                    emit(ApiResponse.Error("Response body is null"))
//                }
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error updating language")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }
//
//    override suspend fun deleteLanguage(languageId: String): Flow<ApiResponse<Unit>> = flow {
//        try {
//            val response = homeService.deleteLanguage(languageId)
//            if (response.isSuccessful) {
//                emit(ApiResponse.Success(Unit))
//            } else {
//                emit(ApiResponse.Error("Error: ${response.code()} - ${response.message()}"))
//            }
//        } catch (e: Exception) {
//            Timber.e(e, "Error deleting language")
//            emit(ApiResponse.Error(e.message ?: "Unknown error occurred"))
//        }
//    }

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService
) : HomeRepository, BaseRepository() {
    override suspend fun getProfile(): Flow<ApiResponse<UserProfileResponse>> {
        return apiCall { homeService.getProfile() }
    }

    override suspend fun updateProfile(profile: UserProfileUpdate): Flow<ApiResponse<UserProfileResponse>> {
        return apiCall { homeService.updateProfile(profile) }
    }

    override suspend fun createProfile(profile: UserProfileCreate): Flow<ApiResponse<UserProfileResponse>> {
        return apiCall { homeService.createProfile(profile) }
    }

    override suspend fun deleteProfile(): Flow<ApiResponse<Unit>> {
        return apiCall { homeService.deleteProfile() }
    }

    override suspend fun getProfileCompletion(): Flow<ApiResponse<Map<String, Any>>> {
        return apiCall { homeService.getProfileCompletion() }
    }

    override suspend fun getProfileDetails(): Flow<ApiResponse<UserProfileDetails>> {
        return apiCall { homeService.getProfileDetails() }
    }

    // Skills endpoints
    override suspend fun getSkills(): Flow<ApiResponse<List<ProfileSkillResponse>>> {
        return apiCall { homeService.getSkills() }
    }

    override suspend fun addSkill(skill: ProfileSkillCreate): Flow<ApiResponse<ProfileSkillResponse>> {
        return apiCall { homeService.addSkill(skill) }
    }

    override suspend fun updateSkill(skillId: String, skill: ProfileSkillUpdate): Flow<ApiResponse<ProfileSkillResponse>> {
        return apiCall { homeService.updateSkill(skillId, skill) }
    }

    override suspend fun deleteSkill(skillId: String): Flow<ApiResponse<Unit>> {
        return apiCall { homeService.deleteSkill(skillId) }
    }

    // Experience endpoints
    override suspend fun getExperience(): Flow<ApiResponse<List<ProfileExperienceResponse>>> {
        return apiCall { homeService.getExperience() }
    }

    override suspend fun addExperience(experience: ProfileExperienceCreate): Flow<ApiResponse<ProfileExperienceResponse>> {
        return apiCall { homeService.addExperience(experience) }
    }

    override suspend fun updateExperience(experienceId: String, experience: ProfileExperienceUpdate): Flow<ApiResponse<ProfileExperienceResponse>> {
        return apiCall { homeService.updateExperience(experienceId, experience) }
    }

    override suspend fun deleteExperience(experienceId: String): Flow<ApiResponse<Unit>> {
        return apiCall { homeService.deleteExperience(experienceId) }
    }

    // Education endpoints
    override suspend fun getEducation(): Flow<ApiResponse<List<ProfileEducationResponse>>> {
        return apiCall { homeService.getEducation() }
    }

    override suspend fun addEducation(education: ProfileEducationCreate): Flow<ApiResponse<ProfileEducationResponse>> {
        return apiCall { homeService.addEducation(education) }
    }

    override suspend fun updateEducation(educationId: String, education: ProfileEducationUpdate): Flow<ApiResponse<ProfileEducationResponse>> {
        return apiCall { homeService.updateEducation(educationId, education) }
    }

    override suspend fun deleteEducation(educationId: String): Flow<ApiResponse<Unit>> {
        return apiCall { homeService.deleteEducation(educationId) }
    }

    // Projects endpoints
    override suspend fun getProjects(): Flow<ApiResponse<List<ProfileProjectResponse>>> {
        return apiCall { homeService.getProjects() }
    }

    override suspend fun addProject(project: ProfileProjectCreate): Flow<ApiResponse<ProfileProjectResponse>> {
        return apiCall { homeService.addProject(project) }
    }

    override suspend fun updateProject(projectId: String, project: ProfileProjectUpdate): Flow<ApiResponse<ProfileProjectResponse>> {
        return apiCall { homeService.updateProject(projectId, project) }
    }

    override suspend fun deleteProject(projectId: String): Flow<ApiResponse<Unit>> {
        return apiCall { homeService.deleteProject(projectId) }
    }

    // Certifications endpoints
    override suspend fun getCertifications(): Flow<ApiResponse<List<ProfileCertificationResponse>>> {
        return apiCall { homeService.getCertifications() }
    }

    override suspend fun addCertification(certification: ProfileCertificationCreate): Flow<ApiResponse<ProfileCertificationResponse>> {
        return apiCall { homeService.addCertification(certification) }
    }

    override suspend fun updateCertification(certificationId: String, certification: ProfileCertificationUpdate): Flow<ApiResponse<ProfileCertificationResponse>> {
        return apiCall { homeService.updateCertification(certificationId, certification) }
    }

    override suspend fun deleteCertification(certificationId: String): Flow<ApiResponse<Unit>> {
        return apiCall { homeService.deleteCertification(certificationId) }
    }

    // Languages endpoints
    override suspend fun getLanguages(): Flow<ApiResponse<List<ProfileLanguageResponse>>> {
        return apiCall { homeService.getLanguages() }
    }

    override suspend fun addLanguage(language: ProfileLanguageCreate): Flow<ApiResponse<ProfileLanguageResponse>> {
        return apiCall { homeService.addLanguage(language) }
    }

    override suspend fun updateLanguage(languageId: String, language: ProfileLanguageUpdate): Flow<ApiResponse<ProfileLanguageResponse>> {
        return apiCall { homeService.updateLanguage(languageId, language) }
    }

    override suspend fun deleteLanguage(languageId: String): Flow<ApiResponse<Unit>> {
        return apiCall { homeService.deleteLanguage(languageId) }
    }

    // Analytics endpoints
    override suspend fun getProfileCompletionAnalytics(): Flow<ApiResponse<Map<String, Any>>> {
        return apiCall { homeService.getProfileCompletionAnalytics() }
    }

    override suspend fun getResumeStats(): Flow<ApiResponse<Map<String, Any>>> {
        return apiCall { homeService.getResumeStats() }
    }
}