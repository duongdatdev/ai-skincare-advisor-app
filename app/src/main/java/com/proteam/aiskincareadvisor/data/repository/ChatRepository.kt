package com.proteam.aiskincareadvisor.data.repository

import com.proteam.aiskincareadvisor.data.api.AIClient
import com.proteam.aiskincareadvisor.data.api.ChatModels
import com.azure.ai.inference.models.ChatCompletions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository {
    private val client = AIClient.chatClient

    suspend fun getAIResponse(userMessage: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            // Tạo chuỗi messages: system + user
            val systemMsg = ChatModels.systemMessage()
            val userMsg   = ChatModels.userMessage(userMessage)
            val options   = ChatModels.makeOptions(listOf(systemMsg, userMsg))

            // Gọi API đồng bộ
            val response: ChatCompletions = client.complete(options)
            val reply = response.choice
                .message
                .content
                .trim()

            Result.success(reply)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
