package com.whatsapp.task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.whatsapp.task.ui.screens.authorization.AuthorizationScreen
import com.whatsapp.task.ui.screens.chat.ChatDetailScreen
import com.whatsapp.task.ui.screens.chats.ChatsScreen
import com.whatsapp.task.ui.screens.chats.ChatsEditScreen
import com.whatsapp.task.ui.theme.WhatsAppTaskTheme
import com.whatsapp.task.viewmodel.ChatsViewModel

class MainActivity : ComponentActivity() {

    // ==========================================================
    // VIEW MODEL
    // ==========================================================

    private lateinit var chatsViewModel: ChatsViewModel

    // ==========================================================
    // SCREEN STATE
    // ==========================================================

    private var currentScreen by mutableStateOf(
        Screen.AUTHORIZATION
    )

    private var selectedChatId by mutableStateOf<String?>(null)

    // ==========================================================
    // ON CREATE
    // ==========================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ------------------------------------------------------
        // CHATS VIEW MODEL
        // ------------------------------------------------------

        chatsViewModel =
            ViewModelProvider(this)[ChatsViewModel::class.java]

        // ------------------------------------------------------
        // COMPOSE
        // ------------------------------------------------------

        setContent {

            WhatsAppTaskTheme {

                when (currentScreen) {

                    // ==================================================
                    // AUTHORIZATION
                    // ==================================================

                    Screen.AUTHORIZATION -> {

                        AuthorizationScreen(
                            onDone = {
                                currentScreen = Screen.CHATS
                            }
                        )
                    }

                    // ==================================================
                    // CHATS
                    // ==================================================

                    Screen.CHATS -> {

                        ChatsScreen(
                            viewModel = chatsViewModel,

                            onChatClick = { chatId ->

                                openChat(chatId)
                            },

                            onEditClick = {

                                currentScreen = Screen.CHATS_EDIT
                            }
                        )
                    }

                    // ==================================================
                    // EDIT CHATS
                    // ==================================================

                    Screen.CHATS_EDIT -> {

                        ChatsEditScreen(
                            viewModel = chatsViewModel,

                            onDone = {

                                currentScreen = Screen.CHATS
                            }
                        )
                    }

                    // ==================================================
                    // CHAT DETAIL
                    // ==================================================

                    Screen.CHAT_DETAIL -> {

                        val chatId = selectedChatId

                        if (chatId != null) {

                            ChatDetailScreen(
                                chatId = chatId,

                                onBack = {

                                    closeChat()
                                }
                            )
                        }
                    }
                }
            }
        }

        // ------------------------------------------------------
        // ANDROID BACK BUTTON
        // ------------------------------------------------------

        setupBackButton()
    }

    // ==========================================================
    // OPEN CHAT
    // ==========================================================

    private fun openChat(chatId: String) {

        selectedChatId = chatId

        currentScreen = Screen.CHAT_DETAIL
    }

    // ==========================================================
    // CLOSE CHAT
    // ==========================================================

    private fun closeChat() {

        selectedChatId = null

        currentScreen = Screen.CHATS
    }

    // ==========================================================
    // BACK BUTTON
    // ==========================================================

    private fun setupBackButton() {

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {

                    when (currentScreen) {

                        // --------------------------------------------------
                        // CHAT DETAIL -> CHATS
                        // --------------------------------------------------

                        Screen.CHAT_DETAIL -> {

                            closeChat()
                        }

                        // --------------------------------------------------
                        // EDIT -> CHATS
                        // --------------------------------------------------

                        Screen.CHATS_EDIT -> {

                            currentScreen = Screen.CHATS
                        }

                        // --------------------------------------------------
                        // CHATS -> EXIT
                        // --------------------------------------------------

                        Screen.CHATS -> {

                            isEnabled = false

                            onBackPressedDispatcher.onBackPressed()
                        }

                        // --------------------------------------------------
                        // AUTHORIZATION -> EXIT
                        // --------------------------------------------------

                        Screen.AUTHORIZATION -> {

                            isEnabled = false

                            onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
            }
        )
    }

    // ==========================================================
    // SCREEN ENUM
    // ==========================================================

    private enum class Screen {

        AUTHORIZATION,

        CHATS,

        CHATS_EDIT,

        CHAT_DETAIL
    }
}