package com.omnix.core.data.repository

import com.omnix.core.data.di.IoDispatcher
import com.omnix.core.data.local.dao.ChatMessageDao
import com.omnix.core.data.local.dao.ChatSessionDao
import com.omnix.core.model.AiMode
import com.omnix.core.model.ChatMessage
import com.omnix.core.model.ChatSession
import com.omnix.core.model.MessageRole
import com.omnix.core.model.MessageStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val sessionDao: ChatSessionDao,
    private val messageDao: ChatMessageDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ChatRepository {

    override fun observeActiveSessions(): Flow<List<ChatSession>> =
        sessionDao.observeActiveSessions().map { list -> list.map { it.toDomain() } }

    override fun observeArchivedSessions(): Flow<List<ChatSession>> =
        sessionDao.observeArchivedSessions().map { list -> list.map { it.toDomain() } }

    override fun observeMessages(sessionId: String): Flow<List<ChatMessage>> =
        messageDao.observeMessagesForSession(sessionId).map { list -> list.map { it.toDomain() } }

    override suspend fun getSession(sessionId: String): ChatSession? = withContext(ioDispatcher) {
        sessionDao.getSessionById(sessionId)?.toDomain()
    }

    override suspend fun createSession(title: String): ChatSession = withContext(ioDispatcher) {
        val now = System.currentTimeMillis()
        val session = ChatSession(
            id = UUID.randomUUID().toString(),
            title = title.ifBlank { "New Chat" },
            createdAt = now,
            updatedAt = now,
            pinned = false,
            archived = false,
            messageCount = 0,
            lastMessagePreview = ""
        )
        sessionDao.upsert(session.toEntity())
        session
    }

    override suspend fun renameSession(sessionId: String, title: String) = withContext(ioDispatcher) {
        val existing = sessionDao.getSessionById(sessionId) ?: return@withContext
        sessionDao.update(existing.copy(title = title, updatedAt = System.currentTimeMillis()))
    }

    override suspend fun setPinned(sessionId: String, pinned: Boolean) = withContext(ioDispatcher) {
        sessionDao.setPinned(sessionId, pinned)
    }

    override suspend fun setArchived(sessionId: String, archived: Boolean) = withContext(ioDispatcher) {
        sessionDao.setArchived(sessionId, archived)
    }

    override suspend fun deleteSession(sessionId: String) = withContext(ioDispatcher) {
        val existing = sessionDao.getSessionById(sessionId) ?: return@withContext
        messageDao.deleteAllForSession(sessionId)
        sessionDao.delete(existing)
    }

    override suspend fun sendMessage(
        sessionId: String,
        content: String,
        mode: AiMode
    ): ChatMessage = withContext(ioDispatcher) {
        val now = System.currentTimeMillis()
        val message = ChatMessage(
            id = UUID.randomUUID().toString(),
            sessionId = sessionId,
            role = MessageRole.USER,
            content = content,
            timestamp = now,
            mode = mode,
            status = MessageStatus.COMPLETE
        )
        messageDao.upsert(message.toEntity())

        val session = sessionDao.getSessionById(sessionId)
        if (session != null) {
            sessionDao.update(
                session.copy(
                    updatedAt = now,
                    messageCount = session.messageCount + 1,
                    lastMessagePreview = content.take(120)
                )
            )
        }
        message
    }

    override suspend fun saveMessage(message: ChatMessage) = withContext(ioDispatcher) {
        messageDao.upsert(message.toEntity())
    }

    override suspend fun updateMessageContent(messageId: String, content: String) = withContext(ioDispatcher) {
        val existing = messageDao.getMessageById(messageId) ?: return@withContext
        messageDao.update(existing.copy(content = content, isEdited = true))
    }

    override suspend fun deleteMessage(messageId: String) = withContext(ioDispatcher) {
        val existing = messageDao.getMessageById(messageId) ?: return@withContext
        messageDao.delete(existing)
    }

    override suspend fun searchMessages(sessionId: String, query: String): List<ChatMessage> =
        withContext(ioDispatcher) {
            messageDao.searchInSession(sessionId, query).map { it.toDomain() }
        }
}
