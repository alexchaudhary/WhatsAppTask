package com.whatsapp.task.data.datasource

import com.whatsapp.task.data.model.Chat
import com.whatsapp.task.data.model.Message
import com.whatsapp.task.data.model.MessageSender

object MockDataSource {

    val chats = listOf(
        Chat(
            id = "1",
            name = "Sarah Johnson",
            profileImage = 0,
            lastMessage = "Hey! How are you doing?",
            timestamp = "10:42 AM",
            unreadCount = 2
        ),
        Chat(
            id = "2",
            name = "John Smith",
            profileImage = 0,
            lastMessage = "See you tomorrow!",
            timestamp = "9:35 AM",
            unreadCount = 1
        ),
        Chat(
            id = "3",
            name = "Design Team",
            profileImage = 0,
            lastMessage = "The new design looks great.",
            timestamp = "Yesterday",
            unreadCount = 5
        ),
        Chat(
            id = "4",
            name = "David Wilson",
            profileImage = 0,
            lastMessage = "Can you send me the files?",
            timestamp = "Yesterday",
            unreadCount = 0
        ),
        Chat(
            id = "5",
            name = "Family Group",
            profileImage = 0,
            lastMessage = "Dinner at 8 PM tonight.",
            timestamp = "Yesterday",
            unreadCount = 8
        ),
        Chat(
            id = "6",
            name = "Emily Davis",
            profileImage = 0,
            lastMessage = "Thank you so much!",
            timestamp = "Monday",
            unreadCount = 0
        ),
        Chat(
            id = "7",
            name = "Alex Brown",
            profileImage = 0,
            lastMessage = "Let's catch up soon.",
            timestamp = "Monday",
            unreadCount = 3
        ),
        Chat(
            id = "8",
            name = "Project Group",
            profileImage = 0,
            lastMessage = "Meeting starts at 11 AM.",
            timestamp = "Sunday",
            unreadCount = 0
        )
    )

    val messages = listOf(
        Message(
            id = "m1",
            chatId = "1",
            text = "Hey! How are you?",
            timestamp = "10:35 AM",
            sender = MessageSender.OTHER
        ),
        Message(
            id = "m2",
            chatId = "1",
            text = "I'm doing great! How about you?",
            timestamp = "10:36 AM",
            sender = MessageSender.ME
        ),
        Message(
            id = "m3",
            chatId = "1",
            text = "I'm good too 😊",
            timestamp = "10:37 AM",
            sender = MessageSender.OTHER
        ),
        Message(
            id = "m4",
            chatId = "1",
            text = "Are you free this evening?",
            timestamp = "10:40 AM",
            sender = MessageSender.OTHER
        ),
        Message(
            id = "m5",
            chatId = "1",
            text = "Yes, I should be free after 7.",
            timestamp = "10:41 AM",
            sender = MessageSender.ME
        ),
        Message(
            id = "m6",
            chatId = "1",
            text = "Perfect! Let's meet then.",
            timestamp = "10:42 AM",
            sender = MessageSender.OTHER
        ),
        Message(
            id = "m7",
            chatId = "2",
            text = "Hi John!",
            timestamp = "9:30 AM",
            sender = MessageSender.ME
        ),
        Message(
            id = "m8",
            chatId = "2",
            text = "Hey! What's up?",
            timestamp = "9:32 AM",
            sender = MessageSender.OTHER
        ),
        Message(
            id = "m9",
            chatId = "2",
            text = "See you tomorrow!",
            timestamp = "9:35 AM",
            sender = MessageSender.OTHER
        ),
        Message(
            id = "m10",
            chatId = "3",
            text = "Did everyone check the latest design?",
            timestamp = "Yesterday",
            sender = MessageSender.OTHER
        ),
        Message(
            id = "m11",
            chatId = "3",
            text = "Yes, it looks really good.",
            timestamp = "Yesterday",
            sender = MessageSender.ME
        ),
        Message(
            id = "m12",
            chatId = "3",
            text = "The new design looks great.",
            timestamp = "Yesterday",
            sender = MessageSender.OTHER
        )
    )
}