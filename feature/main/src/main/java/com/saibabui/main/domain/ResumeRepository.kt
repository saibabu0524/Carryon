package com.saibabui.main.domain

import com.saibabui.database.dao.ResumeDao
import com.saibabui.database.entities.ResumeEntity
import com.saibabui.ui.Resume
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResumeRepository @Inject constructor(
    private val resumeDao: ResumeDao
) {
    fun getAllResumes(): Flow<List<Resume>> {
        return resumeDao.getAllResumes().map { entities ->
            entities.map { entity -> entity.toResume() }
        }
    }

    fun getRecentResumes(limit: Int = 10): Flow<List<Resume>> {
        return resumeDao.getRecentResumes(limit).map { entities ->
            entities.map { entity -> entity.toResume() }
        }
    }

    suspend fun getResumeById(id: String): Resume? {
        return resumeDao.getResumeById(id)?.toResume()
    }

    fun getResumeByIdFlow(id: String): Flow<Resume?> {
        return resumeDao.getResumeByIdFlow(id).map { entity ->
            entity?.toResume()
        }
    }

    suspend fun insertResume(resume: Resume) {
        val entity = ResumeEntity(
            id = UUID.randomUUID().toString(),
            title = resume.title,
            templateName = resume.template,
            templateId = "", // You can add this field to Resume data class
            lastModified = parseDate(resume.lastEdited),
            createdAt = System.currentTimeMillis()
        )
        resumeDao.insertResume(entity)
    }

    suspend fun insertResumeEntity(resume: ResumeEntity) {
        resumeDao.insertResume(resume)
    }

    suspend fun updateResume(resume: ResumeEntity) {
        resumeDao.updateResume(resume)
    }

    suspend fun deleteResume(resume: Resume) {
        resumeDao.deleteResumeById(resume.id ?: "")
    }

    suspend fun deleteResumeById(id: String) {
        resumeDao.deleteResumeById(id)
    }

    fun searchResumes(query: String): Flow<List<Resume>> {
        return resumeDao.searchResumes(query).map { entities ->
            entities.map { entity -> entity.toResume() }
        }
    }

    fun getFavoriteResumes(): Flow<List<Resume>> {
        return resumeDao.getFavoriteResumes().map { entities ->
            entities.map { entity -> entity.toResume() }
        }
    }

    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean) {
        resumeDao.updateFavoriteStatus(id, isFavorite)
    }

    suspend fun getResumeCount(): Int {
        return resumeDao.getResumeCount()
    }

    // Helper function to convert ResumeEntity to Resume
    private fun ResumeEntity.toResume(): Resume {
        return Resume(
            id = this.id,
            title = this.title,
            template = this.templateName,
            lastEdited = formatDate(this.lastModified)
        )
    }

    private fun formatDate(timestamp: Long): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    private fun parseDate(dateString: String): Long {
        return try {
            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            formatter.parse(dateString)?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }
}