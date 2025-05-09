package com.proteam.aiskincareadvisor.data.model

import com.azure.ai.inference.models.ChatCompletionsOptions
import com.azure.ai.inference.models.ChatRequestMessage
import com.azure.ai.inference.models.ChatRequestSystemMessage
import com.azure.ai.inference.models.ChatRequestUserMessage

object ChatModels {
    /** System prompt message */
    fun systemMessage(prompt: String = "Bạn là một chuyên gia phân tích da. Bạn chỉ được phép trả lời các câu hỏi liên quan đến chăm sóc da, phân tích da, sản phẩm dưỡng da, thói quen skincare và các vấn đề liên quan đến làn da. Nếu người dùng hỏi về bất kỳ chủ đề nào không liên quan đến da (ví dụ: lập trình, trò chơi, toán học, v.v), hãy từ chối lịch sự với lý do đó không thuộc chuyên môn của bạn. Trả lời bằng phong cách thân thiện, ngắn gọn, rõ ràng và hoàn toàn bằng tiếng Việt. Không bao giờ vượt ra ngoài vai trò chuyên gia da liễu."): ChatRequestSystemMessage =
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