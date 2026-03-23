package com.raja.kotlinpractice.ui.viewmodel

import com.raja.kotlinpractice.BuildConfig
import androidx.lifecycle.ViewModel

data class HomeUiState(
    val title: String = "Home",
    val summary: String = "",
)

class HomeViewModel : ViewModel() {
    val uiState = HomeUiState(
        summary = buildString {
            appendLine("Environment: ${BuildConfig.ENVIRONMENT}")
            appendLine("Base URL: ${BuildConfig.BASE_URL}")
            appendLine("Architecture: MVVM")
            append("Services: AuthApiService, AccountApiService")
        },
    )
}
