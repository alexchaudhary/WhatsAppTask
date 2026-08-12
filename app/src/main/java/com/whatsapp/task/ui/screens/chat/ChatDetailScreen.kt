package com.whatsapp.task.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.whatsapp.task.data.model.Chat
import com.whatsapp.task.data.model.Message
import com.whatsapp.task.data.model.MessageSender
import com.whatsapp.task.viewmodel.ChatDetailViewModel
import com.whatsapp.task.viewmodel.ChatDetailViewModelFactory

// ==========================================================
// COLORS
// ==========================================================

private val chatBackground = Color(0xFFEFE7DE)
private val headerBackground = Color.White

private val primaryText = Color(0xFF111111)
private val secondaryText = Color(0xFF667781)

private val whatsappBlue = Color(0xFF007AFF)

private val outgoingBubble = Color(0xFFD9FDD3)
private val incomingBubble = Color.White

private val inputBarBackground = Color(0xFFF2F2F7)
private val inputBackground = Color.White

private val dividerColor = Color(0xFFE1E1E1)
private val avatarBackground = Color(0xFFE5E9EF)

// ==========================================================
// MAIN SCREEN
// ==========================================================

@Composable
fun ChatDetailScreen(
    chatId: String,
    onBack: () -> Unit
) {

    // ======================================================
    // VIEW MODEL
    // ======================================================

    val viewModel: ChatDetailViewModel = viewModel(
        key = "chat_$chatId",
        factory = ChatDetailViewModelFactory(
            chatId = chatId
        )
    )

    // ======================================================
    // STATE
    // ======================================================

    val messages by viewModel.messages.collectAsState()

    val chat by viewModel.chat.collectAsState()

    var messageText by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    // ======================================================
    // LOAD DATA
    // ======================================================

    LaunchedEffect(chatId) {

        viewModel.loadMessages()
    }

    // ======================================================
    // AUTO SCROLL
    // ======================================================

    LaunchedEffect(messages.size) {

        if (messages.isNotEmpty()) {

            listState.animateScrollToItem(
                index = messages.lastIndex
            )
        }
    }

    // ======================================================
    // SCREEN
    // ======================================================

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = chatBackground
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {

            // ==================================================
            // HEADER
            // ==================================================

            ChatHeader(
                chat = chat,
                onBack = onBack
            )

            // ==================================================
            // MESSAGES
            // ==================================================

            if (messages.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No messages yet",
                        color = secondaryText,
                        fontSize = 14.sp
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        ),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(
                        5.dp
                    )
                ) {

                    items(
                        items = messages,
                        key = { message ->
                            message.id
                        }
                    ) { message ->

                        MessageBubble(
                            message = message
                        )
                    }
                }
            }

            // ==================================================
            // INPUT
            // ==================================================

            MessageInput(
                text = messageText,

                onTextChange = {
                    messageText = it
                },

                onSend = {

                    val cleanText =
                        messageText.trim()

                    if (cleanText.isNotEmpty()) {

                        viewModel.sendMessage(
                            text = cleanText
                        )

                        messageText = ""
                    }
                }
            )
        }
    }
}

// ==========================================================
// CHAT HEADER
// ==========================================================

@Composable
private fun ChatHeader(
    chat: Chat?,
    onBack: () -> Unit
) {

    val chatName =
        chat?.name ?: "Chat"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerBackground)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ==================================================
            // BACK
            // ==================================================

            IconButton(
                onClick = onBack,
                modifier = Modifier.size(44.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = whatsappBlue,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(1.dp)
            )

            // ==================================================
            // PROFILE IMAGE
            // ==================================================

            ChatHeaderAvatar(
                chat = chat
            )

            Spacer(
                modifier = Modifier.width(9.dp)
            )

            // ==================================================
            // NAME + STATUS
            // ==================================================

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = chatName,
                    color = primaryText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "tap here for contact info",
                    color = secondaryText,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }

            // ==================================================
            // CALL
            // ==================================================

            IconButton(
                onClick = {}
            ) {

                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    tint = whatsappBlue,
                    modifier = Modifier.size(22.dp)
                )
            }

            // ==================================================
            // MORE
            // ==================================================

            IconButton(
                onClick = {}
            ) {

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = primaryText,
                    modifier = Modifier.size(23.dp)
                )
            }
        }

        // ==================================================
        // DIVIDER
        // ==================================================

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(dividerColor)
        )
    }
}

// ==========================================================
// CHAT HEADER AVATAR
// ==========================================================

@Composable
private fun ChatHeaderAvatar(
    chat: Chat?
) {

    if (
        chat != null &&
        chat.profileImage != 0
    ) {

        Image(
            painter = painterResource(
                id = chat.profileImage
            ),
            contentDescription =
            "${chat.name} profile photo",
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

    } else {

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(avatarBackground),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = getInitials(
                    chat?.name ?: "Chat"
                ),
                color = whatsappBlue,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==========================================================
// MESSAGE BUBBLE
// ==========================================================

@Composable
private fun MessageBubble(
    message: Message
) {

    val isMine =
        message.sender == MessageSender.ME

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
        if (isMine) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {

        Column(
            modifier = Modifier
                .widthIn(
                    min = 48.dp,
                    max = 310.dp
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 9.dp,
                        topEnd = 9.dp,

                        bottomStart =
                        if (isMine) {
                            9.dp
                        } else {
                            2.dp
                        },

                        bottomEnd =
                        if (isMine) {
                            2.dp
                        } else {
                            9.dp
                        }
                    )
                )
                .background(
                    if (isMine) {
                        outgoingBubble
                    } else {
                        incomingBubble
                    }
                )
                .padding(
                    start = 10.dp,
                    end = 8.dp,
                    top = 7.dp,
                    bottom = 5.dp
                )
        ) {

            // ==================================================
            // MESSAGE TEXT
            // ==================================================

            Text(
                text = message.text,
                color = primaryText,
                fontSize = 14.sp,
                lineHeight = 19.sp
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            // ==================================================
            // TIME + CHECK
            // ==================================================

            Row(
                modifier = Modifier.align(
                    Alignment.End
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = message.timestamp,
                    color = secondaryText,
                    fontSize = 9.sp
                )

                if (isMine) {

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = "✓✓",
                        color = whatsappBlue,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// ==========================================================
// MESSAGE INPUT
// ==========================================================

@Composable
private fun MessageInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit
) {

    val canSend =
        text.trim().isNotEmpty()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(inputBarBackground)
            .navigationBarsPadding()
            .padding(
                horizontal = 8.dp,
                vertical = 7.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // ==================================================
        // TEXT FIELD
        // ==================================================

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(
                    RoundedCornerShape(21.dp)
                )
                .background(inputBackground)
                .border(
                    width = 1.dp,
                    color = dividerColor,
                    shape = RoundedCornerShape(21.dp)
                )
                .padding(
                    horizontal = 15.dp,
                    vertical = 9.dp
                ),
            contentAlignment = Alignment.CenterStart
        ) {

            if (text.isEmpty()) {

                Text(
                    text = "Message",
                    color = secondaryText,
                    fontSize = 14.sp
                )
            }

            BasicTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(
                    color = primaryText,
                    fontSize = 14.sp
                ),
                cursorBrush = SolidColor(
                    whatsappBlue
                )
            )
        }

        Spacer(
            modifier = Modifier.width(3.dp)
        )

        // ==================================================
        // SEND BUTTON
        // ==================================================

        IconButton(
            onClick = onSend,
            enabled = canSend,
            modifier = Modifier.size(44.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint =
                if (canSend) {
                    whatsappBlue
                } else {
                    Color.LightGray
                },
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

// ==========================================================
// INITIALS
// ==========================================================

private fun getInitials(
    name: String
): String {

    val initials = name
        .trim()
        .split(" ")
        .filter {
            it.isNotBlank()
        }
        .take(2)
        .mapNotNull {
            it.firstOrNull()?.uppercase()
        }
        .joinToString("")

    return initials.ifEmpty {
        "?"
    }
}