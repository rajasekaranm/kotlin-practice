package com.raja.kotlinpractice.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.raja.kotlinpractice.BuildConfig
import com.raja.kotlinpractice.data.repository.AuthRepository

data class AuthUiState(
    val title: String = "Authentication",
    val actions: List<String> = emptyList(),
    val endpointBaseUrl: String = "",
)

class AuthViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    val uiState = AuthUiState(
        actions = listOf(
            "Login",
            "Registration",
            "Reset password",
            "Guest access token",
        ),
        endpointBaseUrl = BuildConfig.BASE_URL,
    )

    fun repositoryName(): String = authRepository::class.java.simpleName
}
