// Updated HomeViewModel.kt
package com.saibabui.main.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.database.dao.UserDao
import com.saibabui.database.dao.ResumeDao
import com.saibabui.database.entities.ResumeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserDao,
    private val resumeDao: ResumeDao
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _userName = MutableStateFlow("User")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _recentResumes = MutableStateFlow<List<ResumeCardData>>(emptyList())
    val recentResumes: StateFlow<List<ResumeCardData>> = _recentResumes.asStateFlow()

    private val _resumeTips = MutableStateFlow(
        listOf(
            Tip("Tailor your resume to the job description for better results."),
            Tip("Use strong action verbs to describe your achievements."),
            Tip("Keep your resume concise and limit it to 1-2 pages."),
            Tip("Include quantifiable achievements with numbers and percentages."),
            Tip("Proofread your resume multiple times before submitting."),
            Tip("Use a clean, professional format that's easy to read."),
            Tip("Include relevant keywords from the job posting."),
            Tip("Start bullet points with action verbs like 'Led', 'Created', 'Improved'."),
            Tip("Focus on accomplishments rather than job duties."),
            Tip("Include a professional summary at the top of your resume.")
        )
    )
    val resumeTips: StateFlow<List<Tip>> = _resumeTips.asStateFlow()

    private val _featuredTemplates = MutableStateFlow(
        listOf(
            Template("1", "Modern Resume", "Professional", "modern_preview.png"),
            Template("2", "Classic CV", "Traditional", "classic_preview.png"),
            Template("3", "Creative Portfolio", "Creative", "creative_preview.png"),
            Template("4", "Executive Resume", "Professional", "executive_preview.png"),
            Template("5", "Minimalist CV", "Modern", "minimalist_preview.png")
        )
    )
    val featuredTemplates: StateFlow<List<Template>> = _featuredTemplates.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true

                // Simulate minimum loading time for better UX
                delay(1000)

                // Load user data
                loadUserData()

                // Load recent resumes
                loadRecentResumes()

            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadUserData() {
        try {
            val users = userDao.getAll()
            if (users.isNotEmpty()) {
                val user = users.first()
                _userName.value = "${user.firstName} ${user.lastName}".trim().ifEmpty { "User" }
            }
        } catch (e: Exception) {
            _userName.value = "User"
        }
    }

    private suspend fun loadRecentResumes() {
        resumeDao.getRecentResumes(5).collect { resumeEntities ->
            val recentResumeCards = resumeEntities.map { entity ->
                ResumeCardData(
                    id = entity.id,
                    resumeTitle = entity.title,
                    template = entity.templateName,
                    lastUpdated = formatDate(entity.lastModified),
                    completionStatus = "${entity.completionPercentage}% Complete"
                )
            }
            _recentResumes.value = recentResumeCards
        }
    }

    private fun formatDate(timestamp: Long): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    fun refreshData() {
        loadHomeData()
    }

    fun getRandomTip(): Tip? {
        return _resumeTips.value.randomOrNull()
    }

    // Mock data insertion for testing
    fun insertSampleResumes() {
        viewModelScope.launch(Dispatchers.IO) {
            val sampleResumes = listOf(
                ResumeEntity(
                    id = "1",
                    title = "Software Engineer Resume",
                    templateId = "modern_template",
                    templateName = "Modern Template",
                    completionPercentage = 85,
                    lastModified = System.currentTimeMillis() - (1 * 24 * 60 * 60 * 1000), // 1 day ago
                    createdAt = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000), // 7 days ago
                    isCompleted = false,
                ),
                ResumeEntity(
                    id = "2",
                    title = "Product Manager CV",
                    templateId = "professional_template",
                    templateName = "Professional Template",
                    completionPercentage = 90,
                    lastModified = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000), // 3 days ago
                    createdAt = System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000), // 10 days ago
                    isCompleted = true
                ),
                ResumeEntity(
                    id = "3",
                    title = "Data Scientist Resume",
                    templateId = "creative_template",
                    templateName = "Creative Template",
                    completionPercentage = 75,
                    lastModified = System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000), // 5 days ago
                    createdAt = System.currentTimeMillis() - (15 * 24 * 60 * 60 * 1000), // 15 days ago
                    isCompleted = false
                )
            )

            resumeDao.insertResumes(sampleResumes)
        }
    }
}

// Updated data classes
data class Resume(val id: String, val name: String, val lastModified: String)
data class Tip(val text: String)
data class Template(
    val id: String,
    val name: String,
    val category: String,
    val previewImage: String
)

data class ResumeCardData(
    val id: String,
    val resumeTitle: String,
    val template: String,
    val lastUpdated: String,
    val completionStatus: String
)