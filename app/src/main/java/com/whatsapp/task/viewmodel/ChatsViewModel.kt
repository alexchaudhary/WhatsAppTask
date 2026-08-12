package com.whatsapp.task.viewmodel

import androidx.lifecycle.ViewModel
import com.whatsapp.task.data.model.Chat
import com.whatsapp.task.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatsViewModel : ViewModel() {

    // ==========================================================
    // ALL CHATS
    // ==========================================================

    private var allChats: List<Chat> =
        ChatRepository.getChats()

    // ==========================================================
    // VISIBLE CHATS
    // ==========================================================

    private val _chats =
        MutableStateFlow(
            allChats
        )

    val chats: StateFlow<List<Chat>> =
        _chats.asStateFlow()

    // ==========================================================
    // SEARCH QUERY
    // ==========================================================

    private val _searchQuery =
        MutableStateFlow("")

    val searchQuery: StateFlow<String> =
        _searchQuery.asStateFlow()

    // ==========================================================
    // REFRESH
    // ==========================================================

    fun refresh() {

        allChats =
            ChatRepository.getChats()

        applySearch()
    }

    // ==========================================================
    // SEARCH
    // ==========================================================

    fun searchChats(
        query: String
    ) {

        _searchQuery.value = query

        applySearch()
    }

    // ==========================================================
    // CLEAR SEARCH
    // ==========================================================

    fun clearSearch() {

        _searchQuery.value = ""

        applySearch()
    }

    // ==========================================================
    // APPLY SEARCH
    // ==========================================================

    private fun applySearch() {

        val query =
            _searchQuery.value
                .trim()
                .lowercase()

        if (query.isEmpty()) {

            _chats.value =
                allChats

            return
        }

        _chats.value =
            allChats.filter { chat ->

                chat.name
                    .lowercase()
                    .contains(query) ||

                        chat.lastMessage
                            .lowercase()
                            .contains(query)
            }
    }

    // ==========================================================
    // ARCHIVE
    // ==========================================================

    fun archiveChats(
        selectedChats: Set<String>
    ) {

        if (selectedChats.isEmpty()) {
            return
        }

        ChatRepository.archiveChats(
            selectedChats
        )

        refresh()
    }

    // ==========================================================
    // READ ALL
    // ==========================================================

    fun readAllChats(
        selectedChats: Set<String>
    ) {

        if (selectedChats.isEmpty()) {
            return
        }

        ChatRepository.readAllChats(
            selectedChats
        )

        refresh()
    }

    // ==========================================================
    // DELETE
    // ==========================================================

    fun deleteChats(
        selectedChats: Set<String>
    ) {

        if (selectedChats.isEmpty()) {
            return
        }

        ChatRepository.deleteChats(
            selectedChats
        )

        refresh()
    }
}