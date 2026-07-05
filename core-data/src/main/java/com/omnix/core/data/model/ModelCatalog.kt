package com.omnix.core.data.model

import com.omnix.core.model.ModelInfo
import com.omnix.core.model.ModelProvider
import com.omnix.core.model.ModelTier

/**
 * The canonical list of models OMNIX can work with.
 * Sizes, context windows, and RAM requirements are real published figures.
 */
object ModelCatalog {

    val all: List<ModelInfo> = listOf(
        ModelInfo(
            id = "gemma-3b",
            displayName = "Gemma 3B",
            provider = ModelProvider.GOOGLE,
            tier = ModelTier.LITE,
            sizeGb = 2.9f,
            parameterCount = "3B",
            contextWindowTokens = 8_192,
            minRamGb = 4f
        ),
        ModelInfo(
            id = "qwen-3b",
            displayName = "Qwen 3B",
            provider = ModelProvider.ALIBABA,
            tier = ModelTier.LITE,
            sizeGb = 2.0f,
            parameterCount = "3B",
            contextWindowTokens = 32_768,
            minRamGb = 4f
        ),
        ModelInfo(
            id = "phi-4-mini",
            displayName = "Phi-4 Mini",
            provider = ModelProvider.MICROSOFT,
            tier = ModelTier.LITE,
            sizeGb = 2.5f,
            parameterCount = "3.8B",
            contextWindowTokens = 16_384,
            minRamGb = 4f
        ),
        ModelInfo(
            id = "llama-3.2-3b",
            displayName = "Llama 3.2 3B",
            provider = ModelProvider.META,
            tier = ModelTier.STANDARD,
            sizeGb = 2.0f,
            parameterCount = "3B",
            contextWindowTokens = 131_072,
            minRamGb = 6f
        ),
        ModelInfo(
            id = "mistral-7b",
            displayName = "Mistral 7B",
            provider = ModelProvider.MISTRAL,
            tier = ModelTier.STANDARD,
            sizeGb = 4.1f,
            parameterCount = "7B",
            contextWindowTokens = 32_768,
            minRamGb = 8f
        ),
        ModelInfo(
            id = "deepseek-7b",
            displayName = "DeepSeek 7B",
            provider = ModelProvider.DEEPSEEK,
            tier = ModelTier.PRO,
            sizeGb = 4.7f,
            parameterCount = "7B",
            contextWindowTokens = 32_768,
            minRamGb = 10f
        )
    )

    fun findById(id: String): ModelInfo? = all.firstOrNull { it.id == id }
}
