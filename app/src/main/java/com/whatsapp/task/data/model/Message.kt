package com.whatsapp.task.data.model

data class Message(
    val id: String,
    val chatId: String,
    val text: String,
    val sender: MessageSender,
    val timestamp: String
)

enum class MessageSender {
    ME,
    OTHER
}