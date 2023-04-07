@file:OptIn(ExperimentalComposeUiApi::class)

package com.markusw.chatgptapp.ui.view.activities.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.markusw.chatgptapp.databinding.ActivityMainBinding
import com.markusw.chatgptapp.ui.theme.ChatGptAppTheme
import com.markusw.chatgptapp.ui.view.screens.main.MainScreen
import com.markusw.chatgptapp.ui.viewmodel.main.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {

            val state by viewModel.uiState.collectAsStateWithLifecycle()
            val focusManager = LocalFocusManager.current

            ChatGptAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainScreen(
                        state = state,
                        onSendButtonClick = {
                            viewModel.onPromptSend()
                            focusManager.clearFocus()
                        },
                        onPromptChanged = viewModel::onPromptChanged
                    )
                }
            }
        }
    }
}