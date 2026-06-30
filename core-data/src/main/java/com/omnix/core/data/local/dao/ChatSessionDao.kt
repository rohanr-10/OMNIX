package com.omnix.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.omnix.core.data.local.entity.ChatSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatSessionDao {

    @Query("SELECT * FROM chat_sessions WHERE archived = 0 ORDER BY pinned DESC, updatedAt DESC")
    fun observeActiveSessions(): Flow<List<ChatSessionEntity>>

    @Query("SELECT * FROM chat_sessions WHERE archived = 1 ORDER BY updatedAt DESC")
    fun observeArchivedSessions(): Flow<List<ChatSessionEntity>>

    @Query("SELECT * FROM chat_sessions WHERE id = :sessionId LIMIT 1")
    suspend fun getSessionById(sessionId: String): ChatSessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(session: ChatSessionEntity)

    @Update
    suspend fun update(session: ChatSessionEntity)

    @Delete
    suspend fun delete(session: ChatSessionEntity)

    @Query("UPDATE chat_sessions SET pinned = :pinned WHERE id = :sessionId")
    suspend fun setPinned(sessionId: String, pinned: Boolean)

    @Query("UPDATE chat_sessions SET archived = :archived WHERE id = :sessionId")
    suspend fun setArchived(sessionId: String, archived: Boolean)
}
