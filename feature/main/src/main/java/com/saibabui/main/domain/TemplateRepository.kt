package com.saibabui.main.domain

import com.saibabui.database.dao.TemplateDao
import com.saibabui.database.entities.TemplateEntity
import com.saibabui.main.data.TemplateApiService


import com.saibabui.ui.Template
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemplateRepository @Inject constructor(
    private val templateDao: TemplateDao,
    private val templateApiService: TemplateApiService
) {

    suspend fun getAllTemplates(): List<Template> = withContext(Dispatchers.IO) {
        try {
            // Try to fetch from remote first
            val remoteTemplates = templateApiService.getTemplates()

            // Cache in local database
            templateDao.insertTemplates(remoteTemplates.map { it.toEntity() })

            // Return remote templates
            remoteTemplates
        } catch (e: Exception) {
            // Fallback to local database
            val localTemplates = templateDao.getAllTemplates()
            if (localTemplates.isEmpty()) {
                // If no local data, return default templates
                getDefaultTemplates()
            } else {
                localTemplates.map { it.toTemplate() }
            }
        }
    }

    suspend fun getTemplatesByCategory(category: String): List<Template> = withContext(Dispatchers.IO) {
        templateDao.getTemplatesByCategory(category).map { it.toTemplate() }
    }

    suspend fun markTemplateAsUsed(templateId: String) = withContext(Dispatchers.IO) {
        templateDao.incrementUsageCount(templateId)
    }

    private fun getDefaultTemplates(): List<Template> = listOf(
        Template("1", "Modern Resume", "Professional", "modern_preview.png"),
        Template("2", "Classic CV", "Traditional", "classic_preview.png"),
        Template("3", "Creative Portfolio", "Creative", "creative_preview.png"),
        Template("4", "Tech Resume", "Professional", "tech_preview.png"),
        Template("5", "Minimalist CV", "Minimalist", "minimalist_preview.png"),
        Template("6", "Executive Resume", "Professional", "executive_preview.png"),
        Template("7", "Student CV", "Traditional", "student_preview.png"),
        Template("8", "Designer Portfolio", "Creative", "designer_preview.png")
    )
}


private fun Template.toEntity(): TemplateEntity = TemplateEntity(
    id = id,
    name = name,
    category = category,
    previewImage = previewImage,
    description = description,
    tags = tags,
    usageCount = 0,
    createdAt = System.currentTimeMillis(),
    updatedAt = System.currentTimeMillis()
)

private fun TemplateEntity.toTemplate(): Template = Template(
    id = id,
    name = name,
    category = category,
    previewImage = previewImage,
    description = description ?: "",
    tags = tags ?: emptyList()
)