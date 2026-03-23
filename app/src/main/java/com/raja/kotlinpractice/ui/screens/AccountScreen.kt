package com.raja.kotlinpractice.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raja.kotlinpractice.ui.components.ScreenCard
import com.raja.kotlinpractice.ui.viewmodel.AccountUiState

@Composable
fun AccountScreen(
    uiState: AccountUiState,
    repositoryName: String,
    modifier: Modifier = Modifier,
) {
    ScreenCard(
        title = uiState.title,
        body = buildString {
            appendLine("Repository: $repositoryName")
            appendLine()
            append(uiState.actions.joinToString(separator = "\n") { "- $it" })
        },
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}
