package com.proteam.aiskincareadvisor.data.api

import com.azure.ai.inference.models.ChatCompletionsOptions
import com.azure.ai.inference.models.ChatRequestMessage
import com.azure.ai.inference.models.ChatRequestSystemMessage
import com.azure.ai.inference.models.ChatRequestUserMessage

object ChatModels {
    /** System prompt message */
    fun systemMessage(prompt: String = "You are a skincare AI assistant that provides accurate and personalized advice."): ChatRequestSystemMessage =
        ChatRequestSystemMessage(prompt)

    /** User message */
    fun userMessage(content: String): ChatRequestUserMessage =
        ChatRequestUserMessage(content)

    /** Wrap into options */
    fun makeOptions(messages: List<ChatRequestMessage>): ChatCompletionsOptions {
        return ChatCompletionsOptions(messages)
            .setModel("openai/gpt-4o")
    }
}
