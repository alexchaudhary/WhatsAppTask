package com.whatsapp.task.ui.screens.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatsapp.task.data.model.Chat
import com.whatsapp.task.viewmodel.ChatsViewModel

// ==========================================================
// COLORS
// ==========================================================

private val screenBackground = Color(0xFFF7F9F8)
private val white = Color(0xFFFFFFFF)
private val primaryText = Color(0xFF111B21)
private val secondaryText = Color(0xFF667781)
private val dividerColor = Color(0xFFE9EDEF)
private val blue = Color(0xFF007AFF)
private val green = Color(0xFF25D366)
private val avatarBackground = Color(0xFFE5E9EF)

// ==========================================================
// MAIN SCREEN
// ==========================================================

@Composable
fun ChatsScreen(
    viewModel: ChatsViewModel,
    onChatClick: (String) -> Unit = {},
    onEditClick: () -> Unit = {}
) {

    val chats by viewModel.chats.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = screenBackground
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            ChatsHeader(
                onEditClick = onEditClick
            )

            ChatsActions()

            if (chats.isEmpty()) {

                EmptyChatsState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(white)
                ) {

                    items(
                        items = chats,
                        key = { chat ->
                            chat.id
                        }
                    ) { chat ->

                        ChatItem(
                            chat = chat,
                            onClick = {
                                onChatClick(chat.id)
                            }
                        )
                    }
                }
            }

            ChatsBottomBar()
        }
    }
}

// ==========================================================
// HEADER
// ==========================================================

@Composable
private fun ChatsHeader(
    onEditClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .padding(
                horizontal = 12.dp,
                vertical = 7.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Edit",
            color = blue,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .clickable {
                    onEditClick()
                }
                .padding(
                    horizontal = 4.dp,
                    vertical = 6.dp
                )
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Chats",
            color = primaryText,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        // Search
        Text(
            text = "⌕",
            color = blue,
            fontSize = 28.sp,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .clickable {
                    // Search action later
                }
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        // More
        Text(
            text = "⋮",
            color = primaryText,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .clickable {
                    // More action later
                }
        )
    }
}

// ==========================================================
// ACTION ROW
// ==========================================================

@Composable
private fun ChatsActions() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Broadcast Lists",
            color = blue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "New Group",
            color = blue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// ==========================================================
// CHAT ITEM
// ==========================================================

@Composable
private fun ChatItem(
    chat: Chat,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .clickable {
                onClick()
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ChatProfileImage(
                chat = chat
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = chat.name,
                    color = primaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = chat.lastMessage,
                    color = secondaryText,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = chat.timestamp,
                    color =
                    if (chat.unreadCount > 0) {
                        blue
                    } else {
                        secondaryText
                    },
                    fontSize = 12.sp
                )

                if (chat.unreadCount > 0) {

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    UnreadBadge(
                        count = chat.unreadCount
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 72.dp)
                .height(1.dp)
                .background(dividerColor)
        )
    }
}

// ==========================================================
// PROFILE IMAGE
// ==========================================================

@Composable
private fun ChatProfileImage(
    chat: Chat
) {

    if (chat.profileImage != 0) {

        Image(
            painter = painterResource(
                id = chat.profileImage
            ),
            contentDescription = "${chat.name} profile photo",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

    } else {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(avatarBackground),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = getInitials(chat.name),
                color = blue,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==========================================================
// UNREAD BADGE
// ==========================================================

@Composable
private fun UnreadBadge(
    count: Int
) {

    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(green),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = count.toString(),
            color = white,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ==========================================================
// EMPTY STATE
// ==========================================================

@Composable
private fun EmptyChatsState(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .background(white),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(avatarBackground),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "💬",
                    fontSize = 22.sp
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "No chats yet",
                color = primaryText,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Start a conversation to see it here",
                color = secondaryText,
                fontSize = 14.sp
            )
        }
    }
}

// ==========================================================
// BOTTOM NAVIGATION
// ==========================================================

@Composable
private fun ChatsBottomBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .navigationBarsPadding()
            .padding(
                top = 7.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomNavItem(
            icon = "◉",
            label = "Status",
            selected = false
        )

        BottomNavItem(
            icon = "☎",
            label = "Calls",
            selected = false
        )

        BottomNavItem(
            icon = "◎",
            label = "Camera",
            selected = false
        )

        BottomNavItem(
            icon = "●",
            label = "Chats",
            selected = true
        )

        BottomNavItem(
            icon = "⚙",
            label = "Settings",
            selected = false
        )
    }
}

// ==========================================================
// BOTTOM NAV ITEM
// ==========================================================

@Composable
private fun BottomNavItem(
    icon: String,
    label: String,
    selected: Boolean
) {

    val itemColor =
        if (selected) {
            blue
        } else {
            secondaryText
        }

    Column(
        modifier = Modifier.width(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = icon,
            color = itemColor,
            fontSize = 20.sp,
            fontWeight =
            if (selected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            }
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = label,
            color = itemColor,
            fontSize = 10.sp,
            fontWeight =
            if (selected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            }
        )
    }
}

// ==========================================================
// INITIALS FALLBACK
// ==========================================================

private fun getInitials(
    name: String
): String {

    val parts = name
        .trim()
        .split(" ")
        .filter {
            it.isNotBlank()
        }

    if (parts.isEmpty()) {
        return "?"
    }

    return parts
        .take(2)
        .mapNotNull {
            it.firstOrNull()?.uppercase()
        }
        .joinToString("")
}