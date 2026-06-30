package com.omnix.core.data.repository

import com.omnix.core.model.ChatMessage
import com.omnix.core.model.ChatSession
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun observeActiveSessions(): Flow<List<ChatSession>>

    fun observeArchivedSessions(): Flow<List<ChatSession>>

    fun observeMessages(sessionId: String): Flow<List<ChatMessage>>

    suspend fun getSession(sessionId: String): ChatSession?

    suspend fun createSession(title: String): ChatSession

    suspend fun renameSession(sessionId: String, title: String)

    suspend fun setPinned(sessionId: String, pinned: Boolean)

    suspend fun setArchived(sessionId: String, archived: Boolean)

    suspend fun deleteSession(sessionId: String)

    suspend fun sendMessage(sessionId: String, content: String, mode: com.omnix.core.model.AiMode): ChatMessage

    suspend fun saveMessage(message: ChatMessage)

    suspend fun updateMessageContent(messageId: String, content: String)

    suspend fun deleteMessage(messageId: String)

    suspend fun searchMessages(sessionId: String, query: String): List<ChatMessage>
}
