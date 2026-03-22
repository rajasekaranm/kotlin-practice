package com.raja.kotlinpractice.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.raja.kotlinpractice.BuildConfig
import com.raja.kotlinpractice.data.repository.AuthRepository

enum class AuthRoute {
    LOGIN,
    REGISTRATION,
    FORGOT_PASSWORD,
}

data class AuthUiState(
    val title: String = "Authentication",
    val endpointBaseUrl: String = "",
    val appInfo: String = "",
    val currentRoute: AuthRoute = AuthRoute.LOGIN,
    val isAuthenticated: Boolean = false,
    val loginEmail: String = "",
    val loginPassword: String = "",
    val registrationName: String = "",
    val registrationEmail: String = "",
    val registrationPassword: String = "",
    val forgotPasswordEmail: String = "",
)

class AuthViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    var uiState by mutableStateOf(
        AuthUiState(
            endpointBaseUrl = BuildConfig.BASE_URL,
            appInfo = buildString {
                append("v${BuildConfig.VERSION_NAME}")
                if (BuildConfig.ENVIRONMENT != "production") {
                    append(" • ${BuildConfig.ENVIRONMENT}")
                }
            },
        )
    )
        private set

    fun openLogin() {
        uiState = uiState.copy(currentRoute = AuthRoute.LOGIN)
    }

    fun openRegistration() {
        uiState = uiState.copy(currentRoute = AuthRoute.REGISTRATION)
    }

    fun openForgotPassword() {
        uiState = uiState.copy(currentRoute = AuthRoute.FORGOT_PASSWORD)
    }

    fun onLoginClick() {
        uiState = uiState.copy(
            isAuthenticated = true,
            currentRoute = AuthRoute.LOGIN,
        )
    }

    fun onRegistrationClick() {
        uiState = uiState.copy(
            isAuthenticated = true,
            currentRoute = AuthRoute.LOGIN,
        )
    }

    fun onLoginEmailChanged(value: String) {
        uiState = uiState.copy(loginEmail = value)
    }

    fun onLoginPasswordChanged(value: String) {
        uiState = uiState.copy(loginPassword = value)
    }

    fun onRegistrationNameChanged(value: String) {
        uiState = uiState.copy(registrationName = value)
    }

    fun onRegistrationEmailChanged(value: String) {
        uiState = uiState.copy(registrationEmail = value)
    }

    fun onRegistrationPasswordChanged(value: String) {
        uiState = uiState.copy(registrationPassword = value)
    }

    fun onForgotPasswordEmailChanged(value: String) {
        uiState = uiState.copy(forgotPasswordEmail = value)
    }

    fun repositoryName(): String = authRepository::class.java.simpleName
}
