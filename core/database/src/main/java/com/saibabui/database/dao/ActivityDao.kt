package com.saibabui.database.dao

import androidx.room.*
import com.saibabui.database.entities.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities WHERE userId = :userId ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getActivityHistory(userId: Int, limit: Int, offset: Int): List<ActivityEntity>

    @Query("SELECT * FROM activities WHERE userId = :userId ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    fun getActivityHistoryFlow(userId: Int, limit: Int, offset: Int): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE userId = :userId AND resumeId = :resumeId ORDER BY createdAt DESC")
    suspend fun getActivityByResume(userId: Int, resumeId: Int): List<ActivityEntity>

    @Query("SELECT * FROM activities WHERE userId = :userId AND action = :actionType ORDER BY createdAt DESC")
    suspend fun getActivityByType(userId: Int, actionType: String): List<ActivityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivities(activities: List<ActivityEntity>)

    @Update
    suspend fun updateActivity(activity: ActivityEntity)

    @Delete
    suspend fun deleteActivity(activity: ActivityEntity)

    @Query("DELETE FROM activities WHERE userId = :userId")
    suspend fun deleteAllActivitiesForUser(userId: Int)

    @Query("DELETE FROM activities WHERE userId = :userId AND resumeId = :resumeId")
    suspend fun deleteActivitiesForResume(userId: Int, resumeId: Int)

    @Query("SELECT COUNT(*) FROM activities WHERE userId = :userId")
    suspend fun getActivityCountForUser(userId: Int): Int
}