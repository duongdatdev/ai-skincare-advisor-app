package com.proteam.aiskincareadvisor.data.repository

import android.content.Context
import android.net.Uri
import com.azure.ai.inference.models.*
import com.proteam.aiskincareadvisor.data.api.AIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class SkinAnalysisRepository(private val context: Context) {
    private val client = AIClient.chatClient

    suspend fun analyzeSkinImage(imageUri: Uri): Result<String> = withContext(Dispatchers.IO) {
        try {
            val imageFile = saveImageToTempFile(imageUri)

            val contentItems = listOf(
                ChatMessageTextContentItem(
                    "Phân tích ảnh da này và cho biết loại da, các vấn đề có thể thấy, tình trạng mụn, và gợi ý chăm sóc da phù hợp bằng tiếng Việt."
                ),
                ChatMessageImageContentItem(imageFile.toPath(), "jpeg") // hoặc "jpg" tùy định dạng
            )

            val messages = listOf(
                ChatRequestSystemMessage("Bạn là một trợ lý phân tích da, nói tiếng Việt."),
                ChatRequestUserMessage.fromContentItems(contentItems)
            )

            val options = ChatCompletionsOptions(messages).apply {
                model = "openai/gpt-4o"
            }

            val response: ChatCompletions = client.complete(options)
            val analysisResult = response.choice.message.content.trim()

            Result.success(analysisResult)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun saveImageToTempFile(imageUri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(imageUri)
            ?: throw IllegalArgumentException("Không thể mở luồng ảnh")
        val tempFile = File.createTempFile("skin_image", ".jpg", context.cacheDir)
        FileOutputStream(tempFile).use { output ->
            inputStream.copyTo(output)
        }
        return tempFile
    }
}
