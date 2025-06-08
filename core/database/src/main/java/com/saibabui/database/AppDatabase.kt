package com.saibabui.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saibabui.database.dao.ResumeDao
import com.saibabui.database.dao.TemplateDao
import com.saibabui.database.dao.UserDao
import com.saibabui.database.entities.ResumeEntity
import com.saibabui.database.entities.TemplateEntity
import com.saibabui.database.entities.User
import com.saibabui.database.utils.Converters

@Database(entities = [User::class,ResumeEntity::class,TemplateEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun resumeDao(): ResumeDao
    abstract fun templateDao(): TemplateDao
}