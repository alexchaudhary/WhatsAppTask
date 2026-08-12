package com.whatsapp.task.ui.screens.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

private val screenBackground = Color(0xFFF2F2F7)
private val white = Color(0xFFFFFFFF)

private val primaryText = Color(0xFF111111)
private val secondaryText = Color(0xFF8E8E93)

private val blue = Color(0xFF007AFF)
private val deleteRed = Color(0xFFFF3B30)

private val separator = Color(0xFFE2E2E7)

private val avatarBackground = Color(0xFFE7EBF0)

private val selectedBackground = Color(0xFFF0F7FF)

private val checkboxBorder = Color(0xFFC7C7CC)

// ==========================================================
// MAIN SCREEN
// ==========================================================

@Composable
fun ChatsEditScreen(
    viewModel: ChatsViewModel,
    onDone: () -> Unit = {}
) {

    val chats by viewModel.chats.collectAsState()

    var selectedChats by remember {
        mutableStateOf<Set<String>>(emptySet())
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = screenBackground
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // ==================================================
            // TOP BAR
            // ==================================================

            EditTopBar(
                selectedCount = selectedChats.size,
                onDone = {
                    selectedChats = emptySet()
                    onDone()
                }
            )

            // ==================================================
            // SECTION HEADER
            // ==================================================

            EditSectionHeader(
                onBroadcastLists = {},
                onNewGroup = {}
            )

            // ==================================================
            // CHAT LIST
            // ==================================================

            if (chats.isEmpty()) {

                EmptyEditState(
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

                        val isSelected =
                            selectedChats.contains(chat.id)

                        EditableChatItem(
                            chat = chat,
                            selected = isSelected,
                            onSelected = {

                                selectedChats =
                                    if (isSelected) {
                                        selectedChats - chat.id
                                    } else {
                                        selectedChats + chat.id
                                    }
                            }
                        )
                    }
                }
            }

            // ==================================================
            // BOTTOM ACTION BAR
            // ==================================================

            EditBottomBar(
                hasSelection = selectedChats.isNotEmpty(),

                onArchive = {

                    viewModel.archiveChats(
                        selectedChats
                    )

                    selectedChats = emptySet()
                },

                onReadAll = {

                    viewModel.readAllChats(
                        selectedChats
                    )

                    selectedChats = emptySet()
                },

                onDelete = {

                    viewModel.deleteChats(
                        selectedChats
                    )

                    selectedChats = emptySet()
                }
            )
        }
    }
}

// ==========================================================
// TOP BAR
// ==========================================================

@Composable
private fun EditTopBar(
    selectedCount: Int,
    onDone: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(white)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Done",
                color = blue,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onDone()
                    }
                    .padding(
                        horizontal = 4.dp,
                        vertical = 6.dp
                    )
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            if (selectedCount > 0) {

                Text(
                    text = "$selectedCount selected",
                    color = secondaryText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier.width(45.dp)
            )
        }

        Text(
            text = "Chats",
            color = primaryText,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(separator)
    )
}

// ==========================================================
// SECTION HEADER
// ==========================================================

@Composable
private fun EditSectionHeader(
    onBroadcastLists: () -> Unit,
    onNewGroup: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 14.dp,
                bottom = 12.dp
            )
    ) {

        Text(
            text = "Chats",
            color = primaryText,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Broadcast Lists",
                color = blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onBroadcastLists()
                    }
                    .padding(
                        horizontal = 2.dp,
                        vertical = 4.dp
                    )
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "New Group",
                color = blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onNewGroup()
                    }
                    .padding(
                        horizontal = 2.dp,
                        vertical = 4.dp
                    )
            )
        }
    }

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}

// ==========================================================
// EDITABLE CHAT ITEM
// ==========================================================

@Composable
private fun EditableChatItem(
    chat: Chat,
    selected: Boolean,
    onSelected: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (selected) {
                    selectedBackground
                } else {
                    white
                }
            )
            .clickable {
                onSelected()
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ==================================================
            // CHECKBOX
            // ==================================================

            SelectionCircle(
                selected = selected,
                onClick = onSelected
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // ==================================================
            // PROFILE IMAGE
            // ==================================================

            EditChatAvatar(
                chat = chat
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // ==================================================
            // CHAT INFORMATION
            // ==================================================

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
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            // ==================================================
            // TIMESTAMP
            // ==================================================

            Text(
                text = chat.timestamp,
                color = secondaryText,
                fontSize = 11.sp,
                maxLines = 1
            )
        }

        // ==================================================
        // DIVIDER
        // ==================================================

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 82.dp)
                .height(1.dp)
                .background(separator)
        )
    }
}

// ==========================================================
// SELECTION CIRCLE
// ==========================================================

@Composable
private fun SelectionCircle(
    selected: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(
                    if (selected) {
                        blue
                    } else {
                        white
                    }
                )
                .border(
                    width = if (selected) {
                        0.dp
                    } else {
                        1.dp
                    },
                    color = if (selected) {
                        Color.Transparent
                    } else {
                        checkboxBorder
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            if (selected) {

                Text(
                    text = "✓",
                    color = white,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ==========================================================
// PROFILE IMAGE
// ==========================================================

@Composable
private fun EditChatAvatar(
    chat: Chat
) {

    if (chat.profileImage != 0) {

        Image(
            painter = painterResource(
                id = chat.profileImage
            ),
            contentDescription = "${chat.name} profile photo",
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

    } else {

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(avatarBackground),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = getEditInitials(chat.name),
                color = blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==========================================================
// BOTTOM ACTION BAR
// ==========================================================

@Composable
private fun EditBottomBar(
    hasSelection: Boolean,
    onArchive: () -> Unit,
    onReadAll: () -> Unit,
    onDelete: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(separator)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            EditAction(
                text = "Archive",
                enabled = hasSelection,
                color = blue,
                onClick = onArchive
            )

            EditAction(
                text = "Read All",
                enabled = hasSelection,
                color = blue,
                onClick = onReadAll
            )

            EditAction(
                text = "Delete",
                enabled = hasSelection,
                color = deleteRed,
                onClick = onDelete
            )
        }
    }
}

// ==========================================================
// EDIT ACTION
// ==========================================================

@Composable
private fun EditAction(
    text: String,
    enabled: Boolean,
    color: Color,
    onClick: () -> Unit
) {

    Text(
        text = text,
        color = if (enabled) {
            color
        } else {
            secondaryText
        },
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .clip(
                RoundedCornerShape(8.dp)
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
    )
}

// ==========================================================
// EMPTY STATE
// ==========================================================

@Composable
private fun EmptyEditState(
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
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(avatarBackground),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "●",
                    color = secondaryText,
                    fontSize = 20.sp
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "No chats",
                color = primaryText,
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Your conversations will appear here",
                color = secondaryText,
                fontSize = 14.sp
            )
        }
    }
}

// ==========================================================
// INITIALS
// ==========================================================

private fun getEditInitials(
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

    return if (initials.isEmpty()) {
        "?"
    } else {
        initials
    }
}