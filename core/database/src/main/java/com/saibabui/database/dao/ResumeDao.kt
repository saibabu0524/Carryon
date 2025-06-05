package com.saibabui.database.dao

import com.saibabui.database.entities.ResumeEntity


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ResumeDao {

    @Query("SELECT * FROM resumes ORDER BY lastModified DESC")
    fun getAllResumes(): Flow<List<ResumeEntity>>

    @Query("SELECT * FROM resumes ORDER BY lastModified DESC LIMIT :limit")
    fun getRecentResumes(limit: Int = 10): Flow<List<ResumeEntity>>

    @Query("SELECT * FROM resumes WHERE id = :id")
    suspend fun getResumeById(id: String): ResumeEntity?

    @Query("SELECT * FROM resumes WHERE id = :id")
    fun getResumeByIdFlow(id: String): Flow<ResumeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResume(resume: ResumeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResumes(resumes: List<ResumeEntity>)

    @Update
    suspend fun updateResume(resume: ResumeEntity)

    @Delete
    suspend fun deleteResume(resume: ResumeEntity)

    @Query("DELETE FROM resumes WHERE id = :id")
    suspend fun deleteResumeById(id: String)

    @Query("SELECT COUNT(*) FROM resumes")
    suspend fun getResumeCount(): Int

    @Query("SELECT * FROM resumes WHERE title LIKE '%' || :searchQuery || '%' OR templateName LIKE '%' || :searchQuery || '%'")
    fun searchResumes(searchQuery: String): Flow<List<ResumeEntity>>

    @Query("SELECT * FROM resumes WHERE isFavorite = 1 ORDER BY lastModified DESC")
    fun getFavoriteResumes(): Flow<List<ResumeEntity>>

    @Query("UPDATE resumes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM resumes WHERE isCompleted = 1 ORDER BY lastModified DESC")
    fun getCompletedResumes(): Flow<List<ResumeEntity>>

    @Query("SELECT * FROM resumes WHERE isCompleted = 0 ORDER BY lastModified DESC")
    fun getIncompleteResumes(): Flow<List<ResumeEntity>>
}