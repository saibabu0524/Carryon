package com.saibabui.database.dao

import androidx.room.*
import com.saibabui.database.entities.BackendResumeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BackendResumeDao {
    @Query("SELECT * FROM backend_resumes WHERE userId = :userId ORDER BY updatedAt DESC")
    suspend fun getResumesByUserId(userId: Int): List<BackendResumeEntity>

    @Query("SELECT * FROM backend_resumes WHERE userId = :userId ORDER BY updatedAt DESC")
    fun getResumesByUserIdFlow(userId: Int): Flow<List<BackendResumeEntity>>

    @Query("SELECT * FROM backend_resumes WHERE id = :resumeId LIMIT 1")
    suspend fun getResumeById(resumeId: Int): BackendResumeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResume(resume: BackendResumeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResumes(resumes: List<BackendResumeEntity>)

    @Update
    suspend fun updateResume(resume: BackendResumeEntity)

    @Delete
    suspend fun deleteResume(resume: BackendResumeEntity)

    @Query("DELETE FROM backend_resumes WHERE id = :resumeId")
    suspend fun deleteResumeById(resumeId: Int)

    @Query("DELETE FROM backend_resumes WHERE userId = :userId")
    suspend fun deleteResumesByUserId(userId: Int)
}