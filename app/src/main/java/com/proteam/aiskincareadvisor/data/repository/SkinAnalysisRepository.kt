package com.proteam.aiskincareadvisor.data.repository

import android.content.Context
import android.net.Uri
import com.azure.ai.inference.models.*
import com.google.firebase.firestore.FirebaseFirestore
import com.proteam.aiskincareadvisor.data.api.AIClient
import com.proteam.aiskincareadvisor.data.firestore.SkinAnalysisStorage
import com.proteam.aiskincareadvisor.data.model.Product
import com.proteam.aiskincareadvisor.data.model.SkinAnalysisResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.text.get

class SkinAnalysisRepository(private val context: Context) {
    private val client = AIClient.chatClient

    suspend fun analyzeSkinImage(imageUri: Uri): Result<String> = withContext(Dispatchers.IO) {
        try {
            val imageFile = saveImageToTempFile(imageUri)

            // Fetch available product categories and skin types from Firestore
//            val productCategories = fetchProductCategories()
            val availableSkinTypes = fetchAvailableSkinTypes()
            val productListText = fetchProductListForAI()

            val contentItems = listOf(
                ChatMessageTextContentItem("Phân tích ảnh da này."),
                ChatMessageTextContentItem("Cho biết: loại da, độ ẩm, độ dầu, tình trạng tổng thể, các vấn đề da."),
                ChatMessageTextContentItem("Sau đó đưa ra từ 3–5 gợi ý chăm sóc và 1–3 mẹo hữu ích."),
                ChatMessageTextContentItem("Phân loại loại da theo một trong các loại sau: ${availableSkinTypes.joinToString(", ")}."),
//                ChatMessageTextContentItem("Gợi ý 2-3 loại sản phẩm phù hợp từ các danh mục: ${productCategories.joinToString(", ")}."),
                ChatMessageTextContentItem("Dưới đây là danh sách sản phẩm có sẵn từ kho dữ liệu của chúng tôi. Mỗi sản phẩm có tên, danh mục và loại da phù hợp."),
                ChatMessageTextContentItem(productListText),
                ChatMessageTextContentItem("Dựa trên loại da người dùng, hãy chọn ra 1–2 sản phẩm phù hợp nhất trong danh sách."),
                ChatMessageTextContentItem("Trình bày theo định dạng sau (KHÔNG dùng dấu ngoặc nhọn hay nháy kép):"),
                ChatMessageTextContentItem("skinType: ..."),
                ChatMessageTextContentItem("hydrationLevel: ..."),
                ChatMessageTextContentItem("oilLevel: ..."),
                ChatMessageTextContentItem("overallCondition: ..."),
                ChatMessageTextContentItem("concerns: [ ... ]"),
                ChatMessageTextContentItem("recommendations: [ ... ]"),
                ChatMessageTextContentItem("tips: [ ... ]"),
                ChatMessageTextContentItem("recommendedProducts: [ ... ]"),
                ChatMessageImageContentItem(imageFile.toPath(), "jpeg")
            )

            val messages = listOf(
                ChatRequestSystemMessage("Bạn là một chuyên gia phân tích da. Bạn chỉ được phép trả lời các câu hỏi liên quan đến chăm sóc da, phân tích da, sản phẩm dưỡng da, thói quen skincare và các vấn đề liên quan đến làn da. Nếu người dùng hỏi về bất kỳ chủ đề nào không liên quan đến da (ví dụ: lập trình, trò chơi, toán học, v.v), hãy từ chối lịch sự với lý do đó không thuộc chuyên môn của bạn. Trả lời bằng phong cách thân thiện, ngắn gọn, rõ ràng và hoàn toàn bằng tiếng Việt. Không bao giờ vượt ra ngoài vai trò chuyên gia da liễu."),
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

    private suspend fun fetchProductListForAI(): String {
        val db = FirebaseFirestore.getInstance()
        val snapshot = db.collection("products").get().await()
        return snapshot.documents.joinToString("\\n") { doc ->
            val name = doc.getString("name") ?: "Không tên"
            val category = doc.getString("category") ?: "Không rõ danh mục"
            val skinTypes = (doc["skinTypes"] as? List<*>)?.joinToString(", ") ?: "Không rõ loại da"
            "- Tên: $name | Danh mục: $category | Phù hợp với: $skinTypes"
        }
    }

    private suspend fun fetchAvailableSkinTypes(): List<String> = withContext(Dispatchers.IO) {
        try {
            val db = FirebaseFirestore.getInstance()
            val snapshot = db.collection("products").get().await()
            val skinTypes = mutableSetOf<String>()

            snapshot.documents.forEach { doc ->
                val product = doc.toObject(Product::class.java)
                product?.skinTypes?.forEach { skinTypes.add(it) }
            }

            return@withContext skinTypes.toList()
        } catch (e: Exception) {
            return@withContext listOf("Dry", "Oily", "Combination", "Normal", "Sensitive")
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

    suspend fun parseTextToSkinResult(text: String): SkinAnalysisResult? {
        fun extractList(key: String): List<String> {
            val regex = Regex("""$key:\s*\[(.*?)\]""", RegexOption.DOT_MATCHES_ALL)
            val raw = regex.find(text)?.groupValues?.get(1) ?: return emptyList()
            return raw.split(",").map { it.trim().removePrefix("\"").removeSuffix("\"") }
        }

        fun extractField(key: String): String {
            val regex = Regex("""$key:\s*(.+)""")
            return regex.find(text)?.groupValues?.get(1)?.trim() ?: ""
        }

        val recommendedProducts = extractList("recommendedProducts")

        // Find product IDs that match the recommended types and user's skin type
        val matchingProductIds = findMatchingProducts(
            extractField("skinType"),
            recommendedProducts
        )

        return SkinAnalysisResult(
            skinType = extractField("skinType"),
            hydrationLevel = extractField("hydrationLevel"),
            oilLevel = extractField("oilLevel"),
            overallCondition = extractField("overallCondition"),
            concerns = extractList("concerns"),
            recommendations = extractList("recommendations"),
            tips = extractList("tips"),
            recommendedProductIds = matchingProductIds
        )
    }

    private suspend fun findMatchingProducts(skinType: String, productNames: List<String>): List<String> {
        return try {
            val db = FirebaseFirestore.getInstance()
            val matchingIds = mutableListOf<String>()

            for (productName in productNames) {
                val query = db.collection("products")
                    .whereEqualTo("name", productName)
                    .whereArrayContains("skinTypes", skinType)
                    .limit(1)

                val results = query.get().await()
                matchingIds.addAll(results.documents.mapNotNull { it.id })
            }

            matchingIds.take(5) // Limit to 5 recommendations
        } catch (e: Exception) {
            emptyList()
        }
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
