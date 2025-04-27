package com.proteam.aiskincareadvisor.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proteam.aiskincareadvisor.data.repository.ChatRepository
import com.proteam.aiskincareadvisor.ui.screens.main.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val repository = ChatRepository()

    private val _messages = MutableStateFlow<List<ChatMessage>>(listOf(
        ChatMessage(
            text = "Hello! I'm your AI skincare assistant. How can I help you today?",
            isFromUser = false
        )
    ))
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun sendMessage(text: String) {
        val userMessage = ChatMessage(text = text, isFromUser = true)
        _messages.value = _messages.value + userMessage

        _isLoading.value = true
        viewModelScope.launch {
            repository.getAIResponse(text).fold(
                onSuccess = { response ->
                    _messages.value = _messages.value + ChatMessage(
                        text = response,
                        isFromUser = false
                    )
                },
                onFailure = { error ->
                    _messages.value = _messages.value + ChatMessage(
                        text = "Sorry, I couldn't process your request: ${error.localizedMessage}",
                        isFromUser = false
                    )
                }
            )
            _isLoading.value = false
        }
    }
}