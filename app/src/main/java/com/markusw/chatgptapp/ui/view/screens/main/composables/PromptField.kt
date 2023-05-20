@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.chatgptapp.ui.view.screens.main.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.markusw.chatgptapp.R

@Composable
fun PromptField(
    value: String,
    onPromptChanged: (String) -> Unit,
    onSendButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isSendButtonEnabled: Boolean = true,
    isSpeaking: Boolean = false,
    onVoiceButtonClick: () -> Unit = {}
) {

    val sendIconTint by animateColorAsState(
        targetValue = if (isSendButtonEnabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
            alpha = 0.4f
        )
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onPromptChanged,
            trailingIcon = {
                IconButton(
                    onClick = onSendButtonClick,
                    enabled = isSendButtonEnabled
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = null,
                        tint = sendIconTint,
                    )
                }
            },
            placeholder = {
                BasicText(text = if(isSpeaking) "Listening..." else "Send a message...")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                cursorColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.background
            ),
            enabled = isEnabled,
            maxLines = 7
        )

        IconButton(onClick = onVoiceButtonClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_voice),
                contentDescription = null,
                tint = if (isSpeaking) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
            )
        }

    }
}