package com.proteam.aiskincareadvisor.data.repository

import android.util.Log
import com.proteam.aiskincareadvisor.data.api.AIClient
import com.proteam.aiskincareadvisor.data.model.ChatModels
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

        val client2 = AIClient.chatModel

        try {
            val prompt = """
                You are a helpful shopping assistant for e-commerce app.
               
                Customer Question: $userMessage
                
                Please provide a helpful, concise response based on the product information and reviews.
            """.trimIndent()



            val response = client2.generateContent(prompt)
            Log.d("AIResponse", "Response: ${response.text}")
            val reply = response.text?.trim() ?: "Sorry, I couldn't generate a response."

            Result.success(reply)
        } catch (e: Exception) {
            Log.d("AIResponse", "Error: ${e.message}")
            Result.failure(e)
        }
    }
}
