package com.proteam.aiskincareadvisor.data.repository

import android.content.Context
import android.net.Uri
import com.azure.ai.inference.models.*
import com.proteam.aiskincareadvisor.data.api.AIClient
import com.proteam.aiskincareadvisor.data.firestore.SkinAnalysisStorage
import com.proteam.aiskincareadvisor.data.model.SkinAnalysisResult
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
                ChatMessageTextContentItem("Phân tích ảnh da này."),
                ChatMessageTextContentItem("Cho biết: loại da, độ ẩm, độ dầu, tình trạng tổng thể, các vấn đề da."),
                ChatMessageTextContentItem("Sau đó đưa ra từ 3–5 gợi ý chăm sóc và 1–3 mẹo hữu ích."),
                ChatMessageTextContentItem("Trình bày theo định dạng sau (KHÔNG dùng dấu ngoặc nhọn hay nháy kép):"),
                ChatMessageTextContentItem("skinType: ..."),
                ChatMessageTextContentItem("hydrationLevel: ..."),
                ChatMessageTextContentItem("oilLevel: ..."),
                ChatMessageTextContentItem("overallCondition: ..."),
                ChatMessageTextContentItem("concerns: [ ... ]"),
                ChatMessageTextContentItem("recommendations: [ ... ]"),
                ChatMessageTextContentItem("tips: [ ... ]"),
                ChatMessageImageContentItem(imageFile.toPath(), "jpeg")
            )

            val messages = listOf(
                ChatRequestSystemMessage("Bạn là một chuyên gia phân tích da. Trả lời bằng tiếng Việt, trình bày thân thiện và rõ ràng theo định dạng được yêu cầu."),
                ChatRequestUserMessage.fromContentItems(contentItems)
            )

            val options = ChatCompletionsOptions(messages).apply {
                model = "openai/gpt-4.1"
            }

            val response: ChatCompletions = client.complete(options)
            val fullText = response.choice.message.content.trim()

            val parsedResult = parseTextToSkinResult(fullText)

            parsedResult?.let {
                SkinAnalysisStorage().saveAnalysisResult(it)
            }


            val cleanedText = formatTextForDisplay(fullText)
            Result.success(cleanedText)
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

    fun parseTextToSkinResult(text: String): SkinAnalysisResult? {
        fun extractList(key: String): List<String> {
            val regex = Regex("""$key:\s*\[(.*?)\]""", RegexOption.DOT_MATCHES_ALL)
            val raw = regex.find(text)?.groupValues?.get(1) ?: return emptyList()
            return raw.split(",").map { it.trim().removePrefix("\"").removeSuffix("\"") }
        }

        fun extractField(key: String): String {
            val regex = Regex("""$key:\s*(.+)""")
            return regex.find(text)?.groupValues?.get(1)?.trim() ?: ""
        }

        return SkinAnalysisResult(
            skinType = extractField("skinType"),
            hydrationLevel = extractField("hydrationLevel"),
            oilLevel = extractField("oilLevel"),
            overallCondition = extractField("overallCondition"),
            concerns = extractList("concerns"),
            recommendations = extractList("recommendations"),
            tips = extractList("tips")
        )
    }

    fun formatTextForDisplay(text: String): String {
        fun formatList(key: String, title: String): String {
            val regex = Regex("""$key:\s*\[(.*?)\]""", RegexOption.DOT_MATCHES_ALL)
            val match = regex.find(text) ?: return ""
            val items = match.groupValues[1].split(",").map { it.trim().replace("\"", "") }
            return items.joinToString("\n- ", prefix = "\n$title:\n- ")
        }

        return buildString {
            append(text.replace("skinType:", "\n• Loại da:")
                .replace("hydrationLevel:", "• Độ ẩm:")
                .replace("oilLevel:", "• Độ dầu:")
                .replace("overallCondition:", "• Tình trạng tổng thể:")
            )
            append(formatList("concerns", "• Vấn đề da"))
            append(formatList("recommendations", "• Gợi ý chăm sóc"))
            append(formatList("tips", "• Mẹo"))
        }.trim()
    }
}
