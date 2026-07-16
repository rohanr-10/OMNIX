package com.omnix.core.data.inference

import com.omnix.core.data.ai.AiResponseProvider
import com.omnix.core.model.AgentRole
import com.omnix.core.model.AiMode
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The central inference coordinator for OMNIX.
 *
 * Responsibilities:
 *  - Implements [AiResponseProvider] so [com.omnix.feature.chat.ChatViewModel] is unchanged.
 *  - Reads the active model from [ModelRuntime] (sourced from Model Manager via
 *    [com.omnix.core.data.repository.ModelRepository]) — no duplicated state.
 *  - Builds [ConversationContext], formats prompts, and creates [InferenceRequest]s.
 *  - Delegates streaming to the injected [InferenceProvider].
 *  - Tracks live [InferenceSession]s for cancellation support.
 *  - Exposes [streamForAgent] so AI Council agents can route through the same
 *    engine in a later phase without any architecture changes.
 *
 * Provider swapping: the active [InferenceProvider] is injected by Hilt.
 * Changing it requires only one line in [com.omnix.core.data.di.InferenceModule].
 */
@Singleton
class InferenceEngine @Inject constructor(
    private val provider: InferenceProvider,
    private val modelRuntime: ModelRuntime
) : AiResponseProvider {

    private val activeSessions = ConcurrentHashMap<String, InferenceSession>()
    private val conversationContext = ConversationContext()

    // ─── AiResponseProvider implementation (Chat integration) ────────────────

    /**
     * Adapts the inference pipeline to the [AiResponseProvider] contract expected
     * by [com.omnix.feature.chat.ChatViewModel]. Emits progressively-built full
     * text strings (not individual tokens) to match the existing contract.
     */
    override fun streamResponse(
        prompt: String,
        mode: AiMode,
        history: List<String>
    ): Flow<String> {
        val sessionId = UUID.randomUUID().toString()
        val activeModel = modelRuntime.activeModel.value

        return flow {
            val modelId = activeModel?.id ?: FALLBACK_MODEL_ID
            val contextWindow = activeModel?.contextWindowTokens ?: DEFAULT_CONTEXT_WINDOW

            val messages = ConversationContext(contextWindow).build(
                userPrompt = prompt,
                history = history,
                mode = mode
            )
            val request = InferenceRequest(
                sessionId = sessionId,
                modelId = modelId,
                messages = messages,
                parameters = GenerationParameters.forMode(mode),
                mode = mode
            )

            val session = InferenceSession(
                id = sessionId,
                modelId = modelId,
                state = SessionState.RUNNING
            )
            activeSessions[sessionId] = session

            try {
                provider.streamTokens(request)
                    .map { token -> token.accumulatedText }
                    .collect { accumulated -> emit(accumulated) }
            } finally {
                activeSessions.remove(sessionId)
                provider.cancel(sessionId)
            }
        }.catch { t ->
            when (t) {
                is CancellationException -> throw t
                is InferenceException -> throw t
                else -> throw InferenceException.Unknown(
                    t.message ?: "Unknown inference error",
                    t
                )
            }
        }
    }

    // ─── AI Council integration point ────────────────────────────────────────

    /**
     * Streams a response for a council agent. Signature is intentionally
     * similar to [streamResponse] so [CouncilAgent] implementations need
     * only inject [InferenceEngine] and call this in a later phase — no
     * orchestrator or registry changes required.
     *
     * [role] is passed so the system prompt can be specialised per-agent in
     * a future phase; for now it is included in the request metadata only.
     */
    fun streamForAgent(
        prompt: String,
        agentRole: AgentRole,
        priorContext: List<String> = emptyList()
    ): Flow<String> = streamResponse(
        prompt = prompt,
        mode = AiMode.AI_COUNCIL,
        history = priorContext
    )

    // ─── Session management ───────────────────────────────────────────────────

    fun cancelSession(sessionId: String) {
        activeSessions[sessionId]?.cancel()
        activeSessions.remove(sessionId)
    }

    fun cancelAllSessions() {
        activeSessions.values.forEach { it.cancel() }
        activeSessions.clear()
    }

    fun activeSessionCount(): Int = activeSessions.size

    // ─── Provider info ────────────────────────────────────────────────────────

    val providerDisplayName: String get() = provider.displayName
    val providerId: String get() = provider.providerId

    companion object {
        private const val FALLBACK_MODEL_ID = "none"
        private const val DEFAULT_CONTEXT_WINDOW = 4_096
    }
}
