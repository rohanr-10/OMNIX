package com.omnix.core.data.inference

/**
 * Lifecycle state of the [InferenceEngine] itself (not of an individual session).
 */
enum class InferenceEngineState {
    /** No provider loaded yet; first request will trigger initialization. */
    IDLE,
    /** Engine is loading / warming up a provider. */
    INITIALIZING,
    /** Ready to accept requests. */
    READY,
    /** Actively generating a response. */
    GENERATING,
    /** A non-fatal error occurred; engine can recover on next request. */
    ERROR
}

/**
 * Lifecycle state of one individual generation [InferenceSession].
 */
enum class SessionState {
    PENDING,
    RUNNING,
    COMPLETED,
    CANCELLED,
    FAILED
}
