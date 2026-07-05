package com.omnix.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.omnix.core.data.local.dao.ChatMessageDao
import com.omnix.core.data.local.dao.ChatSessionDao
import com.omnix.core.data.local.dao.ModelStateDao
import com.omnix.core.data.local.entity.ChatMessageEntity
import com.omnix.core.data.local.entity.ChatSessionEntity
import com.omnix.core.data.local.entity.ModelStateEntity

@Database(
    entities = [
        ChatSessionEntity::class,
        ChatMessageEntity::class,
        ModelStateEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class OmnixDatabase : RoomDatabase() {
    abstract fun chatSessionDao(): ChatSessionDao
    abstract fun chatMessageDao(): ChatMessageDao
    abstract fun modelStateDao(): ModelStateDao

    companion object {
        const val DATABASE_NAME = "omnix.db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS model_states (
                        modelId TEXT NOT NULL PRIMARY KEY,
                        downloadState TEXT NOT NULL,
                        downloadProgress REAL NOT NULL,
                        isActive INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }
    }
}
