package com.omnix.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.omnix.core.data.local.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {

    @Query("SELECT * FROM chat_messages WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun observeMessagesForSession(sessionId: String): Flow<List<ChatMessageEntity>>

    @Query("SELECT * FROM chat_messages WHERE id = :messageId LIMIT 1")
    suspend fun getMessageById(messageId: String): ChatMessageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(message: ChatMessageEntity)

    @Update
    suspend fun update(message: ChatMessageEntity)

    @Delete
    suspend fun delete(message: ChatMessageEntity)

    @Query(
        """
        SELECT * FROM chat_messages
        WHERE sessionId = :sessionId AND content LIKE '%' || :query || '%'
        ORDER BY timestamp DESC
        """
    )
    suspend fun searchInSession(sessionId: String, query: String): List<ChatMessageEntity>

    @Query("DELETE FROM chat_messages WHERE sessionId = :sessionId")
    suspend fun deleteAllForSession(sessionId: String)
}
