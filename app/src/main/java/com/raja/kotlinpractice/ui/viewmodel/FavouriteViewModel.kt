package com.raja.kotlinpractice.ui.viewmodel

import androidx.lifecycle.ViewModel

data class FavouriteUiState(
    val title: String = "Favourite",
    val summary: String = "Keep quick actions and saved items here.",
)

class FavouriteViewModel : ViewModel() {
    val uiState = FavouriteUiState()
}
