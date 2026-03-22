package com.raja.kotlinpractice.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raja.kotlinpractice.ui.components.ScreenCard
import com.raja.kotlinpractice.ui.viewmodel.FavouriteUiState

@Composable
fun FavouriteScreen(
    uiState: FavouriteUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ScreenCard(
            title = uiState.title,
            body = uiState.summary
        )
    }
}
