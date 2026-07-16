package com.omnix.core.data.inference

/**
 * Sealed hierarchy of every failure mode the inference layer can produce.
 * Callers catch [InferenceException] rather than raw Throwables so error
 * handling in ViewModels stays clean and exhaustive.
 */
sealed class InferenceException(message: String, cause: Throwable? = null) :
    Exception(message, cause) {

    /** No model is currently active in Model Manager. */
    class NoActiveModel : InferenceException(
        "No active model selected. Open Model Manager to choose one."
    )

    /** The requested provider is not available on this device. */
    class ProviderUnavailable(providerId: String) : InferenceException(
        "Inference provider '$providerId' is not available on this device."
    )

    /** The model's context window was exceeded. */
    class ContextLimitExceeded(modelId: String, tokens: Int, limit: Int) : InferenceException(
        "Context limit exceeded for '$modelId': $tokens tokens > $limit token limit."
    )

    /** The generation was cancelled by the user or system. */
    class GenerationCancelled(sessionId: String) : InferenceException(
        "Generation cancelled for session '$sessionId'."
    )

    /** The provider returned an error during generation. */
    class ProviderError(providerId: String, detail: String, cause: Throwable? = null) :
        InferenceException("Provider '$providerId' error: $detail", cause)

    /** Catch-all for unexpected failures. */
    class Unknown(message: String, cause: Throwable? = null) :
        InferenceException(message, cause)
}
