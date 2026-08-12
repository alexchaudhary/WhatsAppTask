package com.whatsapp.task.data.model

data class Chat(
    val id: String,
    val name: String,
    val profileImage: Int = 0,
    val lastMessage: String = "",
    val timestamp: String = "",
    val unreadCount: Int = 0
)