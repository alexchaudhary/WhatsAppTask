package com.whatsapp.task.data.repository

import com.whatsapp.task.R
import com.whatsapp.task.data.model.Chat
import com.whatsapp.task.data.model.Message
import com.whatsapp.task.data.model.MessageSender
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ChatRepository {

    // ==========================================================
    // CHAT LIST
    // ==========================================================

    private val chats = mutableListOf(

        Chat(
            id = "1",
            name = "Martha Craig",
            profileImage = R.drawable.martha,
            lastMessage = "Hey, how are you?",
            timestamp = "10:42 AM",
            unreadCount = 2
        ),

        Chat(
            id = "2",
            name = "Andrew Parker",
            profileImage = R.drawable.andrew,
            lastMessage = "See you tomorrow.",
            timestamp = "9:18 AM",
            unreadCount = 0
        ),

        Chat(
            id = "3",
            name = "Karen Castillo",
            profileImage = R.drawable.karen,
            lastMessage = "Thank you!",
            timestamp = "Yesterday",
            unreadCount = 1
        ),

        Chat(
            id = "4",
            name = "Maximillian Jacobson",
            profileImage = R.drawable.maximillian,
            lastMessage = "Let's talk later.",
            timestamp = "Yesterday",
            unreadCount = 0
        )
    )

    // ==========================================================
    // ARCHIVED CHAT IDS
    // ==========================================================

    private val archivedChats =
        mutableSetOf<String>()

    // ==========================================================
    // MESSAGES
    // ==========================================================

    private val messages =
        mutableMapOf<String, MutableList<Message>>()

    // ==========================================================
    // INITIAL DATA
    // ==========================================================

    init {

        messages["1"] = mutableListOf(

            Message(
                id = "1_1",
                chatId = "1",
                text = "Hey!",
                sender = MessageSender.OTHER,
                timestamp = "10:38 AM"
            ),

            Message(
                id = "1_2",
                chatId = "1",
                text = "Hi Martha, how are you?",
                sender = MessageSender.ME,
                timestamp = "10:39 AM"
            ),

            Message(
                id = "1_3",
                chatId = "1",
                text = "I'm good. How about you?",
                sender = MessageSender.OTHER,
                timestamp = "10:40 AM"
            ),

            Message(
                id = "1_4",
                chatId = "1",
                text = "I'm doing great!",
                sender = MessageSender.ME,
                timestamp = "10:42 AM"
            )
        )

        messages["2"] = mutableListOf(

            Message(
                id = "2_1",
                chatId = "2",
                text = "Are we meeting tomorrow?",
                sender = MessageSender.OTHER,
                timestamp = "9:15 AM"
            ),

            Message(
                id = "2_2",
                chatId = "2",
                text = "Yes, see you tomorrow.",
                sender = MessageSender.ME,
                timestamp = "9:18 AM"
            )
        )

        messages["3"] = mutableListOf(

            Message(
                id = "3_1",
                chatId = "3",
                text = "Thank you for your help!",
                sender = MessageSender.OTHER,
                timestamp = "Yesterday"
            )
        )

        messages["4"] = mutableListOf(

            Message(
                id = "4_1",
                chatId = "4",
                text = "Let's talk later.",
                sender = MessageSender.OTHER,
                timestamp = "Yesterday"
            )
        )
    }

    // ==========================================================
    // GET CHATS
    // ==========================================================

    fun getChats(): List<Chat> {

        return chats
            .filter { chat ->
                !archivedChats.contains(chat.id)
            }
            .toList()
    }

    // ==========================================================
    // GET CHAT
    // ==========================================================

    fun getChat(chatId: String): Chat? {

        return chats.find {
            it.id == chatId
        }
    }

    // ==========================================================
    // GET MESSAGES
    // ==========================================================

    fun getMessages(
        chatId: String
    ): List<Message> {

        return messages[chatId]
            ?.toList()
            ?: emptyList()
    }

    // ==========================================================
    // SEND MESSAGE
    // ==========================================================

    fun sendMessage(
        chatId: String,
        text: String
    ): Message {

        val cleanText = text.trim()

        val newMessage = Message(
            id = "${chatId}_${System.currentTimeMillis()}",
            chatId = chatId,
            text = cleanText,
            sender = MessageSender.ME,
            timestamp = currentTime()
        )

        val chatMessages =
            messages.getOrPut(chatId) {
                mutableListOf()
            }

        chatMessages.add(newMessage)

        updateChat(
            chatId = chatId,
            lastMessage = cleanText,
            timestamp = newMessage.timestamp
        )

        return newMessage
    }

    // ==========================================================
    // UPDATE CHAT
    // ==========================================================

    private fun updateChat(
        chatId: String,
        lastMessage: String,
        timestamp: String
    ) {

        val index = chats.indexOfFirst {
            it.id == chatId
        }

        if (index == -1) {
            return
        }

        val oldChat = chats[index]

        chats[index] = oldChat.copy(
            lastMessage = lastMessage,
            timestamp = timestamp
        )
    }

    // ==========================================================
    // ARCHIVE CHATS
    // ==========================================================

    fun archiveChats(
        selectedChats: Set<String>
    ) {

        archivedChats.addAll(selectedChats)
    }

    // ==========================================================
    // READ ALL CHATS
    // ==========================================================

    fun readAllChats(
        selectedChats: Set<String>
    ) {

        selectedChats.forEach { chatId ->

            val index = chats.indexOfFirst {
                it.id == chatId
            }

            if (index != -1) {

                val oldChat = chats[index]

                chats[index] = oldChat.copy(
                    unreadCount = 0
                )
            }
        }
    }

    // ==========================================================
    // DELETE CHATS
    // ==========================================================

    fun deleteChats(
        selectedChats: Set<String>
    ) {

        chats.removeAll { chat ->
            selectedChats.contains(chat.id)
        }

        selectedChats.forEach { chatId ->
            messages.remove(chatId)
            archivedChats.remove(chatId)
        }
    }

    // ==========================================================
    // CURRENT TIME
    // ==========================================================

    private fun currentTime(): String {

        val formatter =
            SimpleDateFormat(
                "h:mm a",
                Locale.getDefault()
            )

        return formatter.format(
            Date()
        )
    }
}