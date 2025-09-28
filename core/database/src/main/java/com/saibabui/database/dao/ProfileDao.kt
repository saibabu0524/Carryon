package com.saibabui.database.dao

import androidx.room.*
import com.saibabui.database.entities.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles WHERE userId = :userId LIMIT 1")
    suspend fun getProfileByUserId(userId: Int): ProfileEntity?

    @Query("SELECT * FROM profiles WHERE userId = :userId LIMIT 1")
    fun getProfileByUserIdFlow(userId: Int): Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Update
    suspend fun updateProfile(profile: ProfileEntity)

    @Delete
    suspend fun deleteProfile(profile: ProfileEntity)

    @Query("DELETE FROM profiles WHERE userId = :userId")
    suspend fun deleteProfileByUserId(userId: Int)
}