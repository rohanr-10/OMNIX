package com.omnix.core.data.inference

import kotlinx.coroutines.flow.Flow

/**
 * Contract every inference backend must satisfy.
 *
 * To add a new provider (e.g. a future cloud provider or new local runtime):
 *  1. Implement this interface.
 *  2. Add it to the multibinding set in [com.omnix.core.data.di.InferenceModule].
 *  3. Change [com.omnix.core.data.di.InferenceModule.bindActiveProvider] to point to it.
 *
 * Nothing else in the architecture needs to change.
 */
interface InferenceProvider {

    /** Stable, unique identifier for this provider (e.g. "simulated", "llamacpp"). */
    val providerId: String

    /** Human-readable name for settings/debug UI. */
    val displayName: String

    /** Returns true if this provider can actually run on the current device/session. */
    suspend fun isAvailable(): Boolean

    /**
     * Streams [StreamingToken]s for the given [request].
     *
     * - Implementations must emit at least one token.
     * - The final emission must have [StreamingToken.isFinal] == true.
     * - The flow must complete (not just suspend) after the final token.
     * - If the underlying job is cancelled, the flow must cancel cleanly
     *   without swallowing the CancellationException.
     */
    fun streamTokens(request: InferenceRequest): Flow<StreamingToken>

    /**
     * Signals the provider to stop generation for [sessionId] if it is still
     * running. Implementations that manage native resources should release
     * them here. This is always called after a session ends (normally or via
     * cancellation) so providers can clean up eagerly.
     */
    suspend fun cancel(sessionId: String)
}
