package com.saibabui.database.dao

import androidx.room.*
import com.saibabui.database.entities.CollaboratorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollaboratorDao {
    @Query("SELECT * FROM collaborators WHERE resumeId = :resumeId")
    suspend fun getCollaboratorsForResume(resumeId: Int): List<CollaboratorEntity>

    @Query("SELECT * FROM collaborators WHERE resumeId = :resumeId")
    fun getCollaboratorsForResumeFlow(resumeId: Int): Flow<List<CollaboratorEntity>>

    @Query("SELECT * FROM collaborators WHERE userId = :userId")
    suspend fun getCollaborationsForUser(userId: Int): List<CollaboratorEntity>

    @Query("SELECT * FROM collaborators WHERE resumeId = :resumeId AND userId = :userId LIMIT 1")
    suspend fun getCollaborator(resumeId: Int, userId: Int): CollaboratorEntity?

    @Query("SELECT * FROM collaborators WHERE resumeId = :resumeId AND email = :email LIMIT 1")
    suspend fun getCollaboratorByEmail(resumeId: Int, email: String): CollaboratorEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollaborator(collaborator: CollaboratorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollaborators(collaborators: List<CollaboratorEntity>)

    @Update
    suspend fun updateCollaborator(collaborator: CollaboratorEntity)

    @Delete
    suspend fun deleteCollaborator(collaborator: CollaboratorEntity)

    @Query("DELETE FROM collaborators WHERE id = :collaboratorId")
    suspend fun deleteCollaboratorById(collaboratorId: Int)

    @Query("DELETE FROM collaborators WHERE resumeId = :resumeId AND userId = :userId")
    suspend fun removeCollaborator(resumeId: Int, userId: Int)

    @Query("DELETE FROM collaborators WHERE resumeId = :resumeId")
    suspend fun removeAllCollaboratorsForResume(resumeId: Int)

    @Query("DELETE FROM collaborators WHERE userId = :userId")
    suspend fun removeAllCollaborationsForUser(userId: Int)
}