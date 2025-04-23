package com.proteam.aiskincareadvisor.data.api

import com.azure.ai.inference.ChatCompletionsClient
import com.azure.ai.inference.ChatCompletionsClientBuilder
import com.azure.core.credential.AzureKeyCredential
import com.proteam.aiskincareadvisor.BuildConfig

object AIClient {
    private val key = AzureKeyCredential(BuildConfig.API_AI_TOKEN)
    private const val ENDPOINT = "https://models.github.ai/inference"

    val chatClient: ChatCompletionsClient by lazy {
        ChatCompletionsClientBuilder()
            .credential(key)
            .endpoint(ENDPOINT)
            .buildClient()
    }
}
