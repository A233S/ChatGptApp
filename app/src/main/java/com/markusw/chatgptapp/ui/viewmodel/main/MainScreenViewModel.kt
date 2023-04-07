package com.markusw.chatgptapp.ui.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.chatgptapp.core.utils.Resource
import com.markusw.chatgptapp.data.model.ChatMessage
import com.markusw.chatgptapp.data.model.MessageRole
import com.markusw.chatgptapp.domain.use_cases.GetChatResponse
import com.markusw.chatgptapp.ui.view.screens.main.MainScreenState
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getChatResponse: GetChatResponse
) : ViewModel() {

    private var _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    fun onPromptSend() {
        viewModelScope.launch(Dispatchers.IO) {

            val prompt = _uiState.value.prompt

            _uiState.update {
                it.copy(
                    chatList = it.chatList + ChatMessage(
                        content = prompt,
                        role = MessageRole.User
                    ),
                    prompt = "",
                    botStatusText = "Bot is typing...",
                    isBotTyping = true
                )
            }

            when(val response = getChatResponse(prompt)) {
                is Resource.Success -> {

                    val responseContent = response.data!!.choices[0].message.content

                    Logger.d(responseContent)

                    _uiState.update {
                        it.copy(
                            chatList = it.chatList + ChatMessage(
                                content = responseContent,
                                role = MessageRole.Bot
                            ),
                            botStatusText = "Bot is online",
                            isBotTyping = false
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            chatList = it.chatList + ChatMessage(
                                content = "Error generating response",
                                role = MessageRole.Bot
                            ),
                            botStatusText = "Bot is online",
                            isBotTyping = false
                        )
                    }
                }
            }
        }
    }

    fun onPromptChanged(prompt: String) {
        _uiState.update { it.copy(prompt = prompt) }
    }

}