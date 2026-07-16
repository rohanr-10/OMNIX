package com.omnix.core.data.inference

import kotlinx.coroutines.Job

/**
 * Represents one live generation session.
 *
 * [job] is the coroutine driving generation — cancelling it propagates
 * cleanly through the provider's Flow and marks the session as CANCELLED.
 */
data class InferenceSession(
    val id: String,
    val modelId: String,
    val state: SessionState,
    val startedAtMs: Long = System.currentTimeMillis(),
    val job: Job? = null
) {
    fun cancel() {
        job?.cancel()
    }

    fun isActive(): Boolean = state == SessionState.RUNNING || state == SessionState.PENDING
}
