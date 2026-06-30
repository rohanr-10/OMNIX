package com.omnix.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omnix.core.data.local.dao.ChatMessageDao
import com.omnix.core.data.local.dao.ChatSessionDao
import com.omnix.core.data.local.entity.ChatMessageEntity
import com.omnix.core.data.local.entity.ChatSessionEntity

@Database(
    entities = [
        ChatSessionEntity::class,
        ChatMessageEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class OmnixDatabase : RoomDatabase() {
    abstract fun chatSessionDao(): ChatSessionDao
    abstract fun chatMessageDao(): ChatMessageDao

    companion object {
        const val DATABASE_NAME = "omnix.db"
    }
}
