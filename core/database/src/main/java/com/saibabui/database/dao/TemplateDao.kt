package com.saibabui.database.dao


import androidx.room.*
import com.saibabui.database.entities.TemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {

    @Query("SELECT * FROM templates ORDER BY usageCount DESC, name ASC")
    suspend fun getAllTemplates(): List<TemplateEntity>

    @Query("SELECT * FROM templates ORDER BY usageCount DESC, name ASC")
    fun getAllTemplatesFlow(): Flow<List<TemplateEntity>>

    @Query("SELECT * FROM templates WHERE category = :category ORDER BY usageCount DESC, name ASC")
    suspend fun getTemplatesByCategory(category: String): List<TemplateEntity>

    @Query("SELECT * FROM templates WHERE id = :id")
    suspend fun getTemplateById(id: String): TemplateEntity?

    @Query("SELECT * FROM templates WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY usageCount DESC")
    suspend fun searchTemplates(query: String): List<TemplateEntity>

    @Query("SELECT * FROM templates WHERE isPopular = 1 ORDER BY usageCount DESC LIMIT :limit")
    suspend fun getPopularTemplates(limit: Int): List<TemplateEntity>

    @Query("SELECT DISTINCT category FROM templates ORDER BY category ASC")
    suspend fun getAllCategories(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: TemplateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<TemplateEntity>)

    @Update
    suspend fun updateTemplate(template: TemplateEntity)

    @Query("UPDATE templates SET usageCount = usageCount + 1, updatedAt = :timestamp WHERE id = :templateId")
    suspend fun incrementUsageCount(templateId: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE templates SET isPopular = :isPopular WHERE id = :templateId")
    suspend fun updatePopularStatus(templateId: String, isPopular: Boolean)

    @Delete
    suspend fun deleteTemplate(template: TemplateEntity)

    @Query("DELETE FROM templates WHERE id = :templateId")
    suspend fun deleteTemplateById(templateId: String)

    @Query("DELETE FROM templates")
    suspend fun deleteAllTemplates()

    @Query("SELECT COUNT(*) FROM templates")
    suspend fun getTemplateCount(): Int
}
