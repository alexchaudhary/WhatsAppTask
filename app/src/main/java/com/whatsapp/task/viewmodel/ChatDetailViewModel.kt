package com.whatsapp.task.viewmodel

import androidx.lifecycle.ViewModel
import com.whatsapp.task.data.model.Chat
import com.whatsapp.task.data.model.Message
import com.whatsapp.task.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatDetailViewModel(
    private val chatId: String
) : ViewModel() {

    // ==========================================================
    // CHAT
    // ==========================================================

    private val _chat =
        MutableStateFlow<Chat?>(
            ChatRepository.getChat(chatId)
        )

    val chat: StateFlow<Chat?> =
        _chat.asStateFlow()

    // ==========================================================
    // MESSAGES
    // ==========================================================

    private val _messages =
        MutableStateFlow<List<Message>>(
            emptyList()
        )

    val messages: StateFlow<List<Message>> =
        _messages.asStateFlow()

    // ==========================================================
    // LOAD CHAT + MESSAGES
    // ==========================================================

    fun loadMessages() {

        _chat.value =
            ChatRepository.getChat(chatId)

        _messages.value =
            ChatRepository.getMessages(
                chatId
            )
    }

    // ==========================================================
    // SEND MESSAGE
    // ==========================================================

    fun sendMessage(
        text: String
    ) {

        val cleanText =
            text.trim()

        if (cleanText.isEmpty()) {
            return
        }

        ChatRepository.sendMessage(
            chatId = chatId,
            text = cleanText
        )

        loadMessages()
    }
}