@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.chatgptapp.ui.view.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markusw.chatgptapp.ui.view.screens.main.composables.ChatItem
import com.markusw.chatgptapp.ui.view.screens.main.composables.MainScreenTopBar
import com.orhanobut.logger.Logger
import com.markusw.chatgptapp.ui.view.screens.main.composables.PromptField as PromptField

@Composable
fun MainScreen(
    state: MainScreenState,
    onSendButtonClick: () -> Unit,
    onPromptChanged: (String) -> Unit
) {

    val scrollState = rememberLazyListState()

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center

            ) {
                PromptField(
                    value = state.prompt,
                    onPromptChanged = onPromptChanged,
                    onSendButtonClick = onSendButtonClick,
                    modifier = Modifier.fillMaxWidth(0.92f)
                )
            }
        },
        topBar = {
            MainScreenTopBar(botStatusText = state.botStatusText)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            state = scrollState
        ) {
            items(state.chatList) { chat ->
                ChatItem(chat = chat)
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        if(state.chatList.isNotEmpty()) {
            Logger.d("Scrolling to last item")
            scrollState.scrollToItem(state.chatList.size - 1)
        }
    }

}