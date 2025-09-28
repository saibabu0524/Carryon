package com.saibabui.database.dao

import androidx.room.*
import com.saibabui.database.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getNotifications(userId: Int, limit: Int, offset: Int): List<NotificationEntity>

    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    fun getNotificationsFlow(userId: Int, limit: Int, offset: Int): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notifications WHERE userId = :userId AND isRead = :isRead ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getNotificationsByReadStatus(userId: Int, isRead: Boolean, limit: Int, offset: Int): List<NotificationEntity>

    @Query("SELECT * FROM notifications WHERE userId = :userId AND notificationType = :notificationType ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getNotificationsByType(userId: Int, notificationType: String, limit: Int, offset: Int): List<NotificationEntity>

    @Query("SELECT * FROM notifications WHERE id = :notificationId LIMIT 1")
    suspend fun getNotificationById(notificationId: Int): NotificationEntity?

    @Query("SELECT * FROM notifications WHERE id = :notificationId LIMIT 1")
    fun getNotificationByIdFlow(notificationId: Int): Flow<NotificationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Update
    suspend fun updateNotification(notification: NotificationEntity)

    @Delete
    suspend fun deleteNotification(notification: NotificationEntity)

    @Query("DELETE FROM notifications WHERE id = :notificationId")
    suspend fun deleteNotificationById(notificationId: Int)

    @Query("DELETE FROM notifications WHERE userId = :userId")
    suspend fun deleteAllNotificationsForUser(userId: Int)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :notificationId")
    suspend fun markNotificationAsRead(notificationId: Int)

    @Query("UPDATE notifications SET isRead = 1 WHERE userId = :userId")
    suspend fun markAllNotificationsAsRead(userId: Int)

    @Query("UPDATE notifications SET isRead = 1 WHERE userId = :userId AND isRead = 0")
    suspend fun markAllUnreadNotificationsAsRead(userId: Int)

    @Query("SELECT COUNT(*) FROM notifications WHERE userId = :userId AND isRead = 0")
    suspend fun getUnreadNotificationCount(userId: Int): Int
}