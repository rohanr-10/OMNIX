package com.omnix.core.data.repository

import com.omnix.core.data.local.entity.ChatMessageEntity
import com.omnix.core.data.local.entity.ChatSessionEntity
import com.omnix.core.model.AiMode
import com.omnix.core.model.ChatMessage
import com.omnix.core.model.ChatSession
import com.omnix.core.model.MessageRole
import com.omnix.core.model.MessageStatus

internal fun ChatSessionEntity.toDomain(): ChatSession = ChatSession(
    id = id,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt,
    pinned = pinned,
    archived = archived,
    messageCount = messageCount,
    lastMessagePreview = lastMessagePreview
)

internal fun ChatSession.toEntity(): ChatSessionEntity = ChatSessionEntity(
    id = id,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt,
    pinned = pinned,
    archived = archived,
    messageCount = messageCount,
    lastMessagePreview = lastMessagePreview
)

internal fun ChatMessageEntity.toDomain(): ChatMessage = ChatMessage(
    id = id,
    sessionId = sessionId,
    role = MessageRole.valueOf(role),
    content = content,
    timestamp = timestamp,
    mode = AiMode.valueOf(mode),
    status = MessageStatus.valueOf(status),
    isEdited = isEdited,
    parentMessageId = parentMessageId
)

internal fun ChatMessage.toEntity(): ChatMessageEntity = ChatMessageEntity(
    id = id,
    sessionId = sessionId,
    role = role.name,
    content = content,
    timestamp = timestamp,
    mode = mode.name,
    status = status.name,
    isEdited = isEdited,
    parentMessageId = parentMessageId
)
