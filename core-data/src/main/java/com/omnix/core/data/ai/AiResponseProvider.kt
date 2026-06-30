package com.omnix.core.data.ai

import com.omnix.core.model.AiMode
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction over "something that can answer a prompt". Phase 1 ships a
 * working simulated implementation so the chat pipeline is fully exercised
 * end-to-end; a later phase swaps this binding for a real on-device model
 * runner without any change to ViewModels or UI.
 */
interface AiResponseProvider {

    /**
     * Streams response chunks for the given prompt under the given mode.
     * Implementations must emit at least one chunk and complete the flow
     * when generation finishes.
     */
    fun streamResponse(prompt: String, mode: AiMode, history: List<String>): Flow<String>
}
