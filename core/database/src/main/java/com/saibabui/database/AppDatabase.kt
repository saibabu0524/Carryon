package com.saibabui.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saibabui.database.dao.*
import com.saibabui.database.entities.*
import com.saibabui.database.utils.Converters

@Database(
    entities = [
        User::class,
        UserEntity::class,
        ResumeEntity::class,
        TemplateEntity::class,
        ProfileEntity::class,
        ActivityEntity::class,
        NotificationEntity::class,
        CollaboratorEntity::class,
        BackendResumeEntity::class
    ], 
    version = 3
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun resumeDao(): ResumeDao
    abstract fun templateDao(): TemplateDao
    abstract fun profileDao(): ProfileDao
    abstract fun activityDao(): ActivityDao
    abstract fun notificationDao(): NotificationDao
    abstract fun collaboratorDao(): CollaboratorDao
    abstract fun backendResumeDao(): BackendResumeDao
}