@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.chatgptapp.ui.view.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markusw.chatgptapp.ui.view.screens.main.composables.ChatItem
import com.markusw.chatgptapp.ui.view.screens.main.composables.MainScreenTopBar
import com.markusw.chatgptapp.ui.view.screens.main.composables.PromptField
import com.orhanobut.logger.Logger

@Composable
fun MainScreen(
    state: MainScreenState,
    onSendButtonClick: () -> Unit,
    onPromptChanged: (String) -> Unit
) {

    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = state.chatList) {
        if(state.chatList.isNotEmpty()) {
            Logger.d("Scrolling to last item")
            scrollState.scrollToItem(state.chatList.size - 1)
        }
    }

    Scaffold(
        bottomBar = {
            PromptField(
                value = state.prompt,
                onPromptChanged = onPromptChanged,
                onSendButtonClick = onSendButtonClick,
                modifier = Modifier.fillMaxWidth()
            )
        },
        topBar = {
            MainScreenTopBar(botStatusText = state.botStatusText)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = scrollState
        ) {
            items(state.chatList) { chat ->
                ChatItem(chat = chat)
            }
        }
    }
}