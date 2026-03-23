package com.raja.kotlinpractice.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raja.kotlinpractice.BuildConfig
import com.raja.kotlinpractice.data.remote.ApiResult
import com.raja.kotlinpractice.data.repository.AuthRepository
import com.raja.kotlinpractice.extensions.isValidEmail
import com.raja.kotlinpractice.extensions.isValidName
import com.raja.kotlinpractice.extensions.isValidPassword
import kotlinx.coroutines.launch

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
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val infoMessage: String? = null,
    val loginEmailError: String? = null,
    val loginPasswordError: String? = null,
    val registrationNameError: String? = null,
    val registrationEmailError: String? = null,
    val registrationPasswordError: String? = null,
    val forgotPasswordEmailError: String? = null,
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
        uiState = uiState.copy(
            currentRoute = AuthRoute.LOGIN,
            errorMessage = null,
            infoMessage = null,
            loginEmailError = null,
            loginPasswordError = null,
        )
    }

    fun openRegistration() {
        uiState = uiState.copy(
            currentRoute = AuthRoute.REGISTRATION,
            errorMessage = null,
            infoMessage = null,
            registrationNameError = null,
            registrationEmailError = null,
            registrationPasswordError = null,
        )
    }

    fun openForgotPassword() {
        uiState = uiState.copy(
            currentRoute = AuthRoute.FORGOT_PASSWORD,
            errorMessage = null,
            infoMessage = null,
            forgotPasswordEmailError = null,
        )
    }

    fun onLoginClick() {
        validateLogin().takeIf { it.hasErrors }?.let { validation ->
            uiState = uiState.copy(
                errorMessage = validation.message,
                infoMessage = null,
                loginEmailError = validation.fieldErrors["loginEmail"],
                loginPasswordError = validation.fieldErrors["loginPassword"],
            )
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null,
                infoMessage = null,
                loginEmailError = null,
                loginPasswordError = null,
            )

            when (val result = authRepository.login(uiState.loginEmail, uiState.loginPassword)) {
                is ApiResult.Success -> {
                    uiState = uiState.copy(
                        isAuthenticated = true,
                        isLoading = false,
                        currentRoute = AuthRoute.LOGIN,
                    )
                }

                is ApiResult.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = result.message,
                    )
                }
            }
        }
    }

    fun onRegistrationClick() {
        validateRegistration().takeIf { it.hasErrors }?.let { validation ->
            uiState = uiState.copy(
                errorMessage = validation.message,
                infoMessage = null,
                registrationNameError = validation.fieldErrors["registrationName"],
                registrationEmailError = validation.fieldErrors["registrationEmail"],
                registrationPasswordError = validation.fieldErrors["registrationPassword"],
            )
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null,
                infoMessage = null,
                registrationNameError = null,
                registrationEmailError = null,
                registrationPasswordError = null,
            )

            when (
                val result = authRepository.register(
                    fullName = uiState.registrationName,
                    email = uiState.registrationEmail,
                    password = uiState.registrationPassword,
                )
            ) {
                is ApiResult.Success -> {
                    uiState = uiState.copy(
                        isAuthenticated = true,
                        isLoading = false,
                        currentRoute = AuthRoute.LOGIN,
                        infoMessage = result.data.message,
                    )
                }

                is ApiResult.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = result.message,
                    )
                }
            }
        }
    }

    fun onForgotPasswordClick() {
        validateForgotPassword().takeIf { it.hasErrors }?.let { validation ->
            uiState = uiState.copy(
                errorMessage = validation.message,
                infoMessage = null,
                forgotPasswordEmailError = validation.fieldErrors["forgotPasswordEmail"],
            )
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null,
                infoMessage = null,
                forgotPasswordEmailError = null,
            )

            when (val result = authRepository.resetPassword(uiState.forgotPasswordEmail)) {
                is ApiResult.Success -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        infoMessage = result.data.message,
                    )
                }

                is ApiResult.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = result.message,
                    )
                }
            }
        }
    }

    fun onLoginEmailChanged(value: String) {
        uiState = uiState.copy(loginEmail = value, loginEmailError = null, errorMessage = null)
    }

    fun onLoginPasswordChanged(value: String) {
        uiState = uiState.copy(loginPassword = value, loginPasswordError = null, errorMessage = null)
    }

    fun onRegistrationNameChanged(value: String) {
        uiState = uiState.copy(registrationName = value, registrationNameError = null, errorMessage = null)
    }

    fun onRegistrationEmailChanged(value: String) {
        uiState = uiState.copy(registrationEmail = value, registrationEmailError = null, errorMessage = null)
    }

    fun onRegistrationPasswordChanged(value: String) {
        uiState = uiState.copy(registrationPassword = value, registrationPasswordError = null, errorMessage = null)
    }

    fun onForgotPasswordEmailChanged(value: String) {
        uiState = uiState.copy(forgotPasswordEmail = value, forgotPasswordEmailError = null, errorMessage = null)
    }

    fun repositoryName(): String = authRepository::class.java.simpleName

    private fun validateLogin(): ValidationResult {
        val errors = buildMap<String, String> {
            if (!uiState.loginEmail.isValidEmail()) {
                put("loginEmail", "Enter a valid email address.")
            }
            if (!uiState.loginPassword.isValidPassword()) {
                put("loginPassword", "Password must be at least 6 characters.")
            }
        }
        return ValidationResult(
            fieldErrors = errors,
            message = errors.values.firstOrNull()
        )
    }

    private fun validateRegistration(): ValidationResult {
        val errors = buildMap<String, String> {
            if (!uiState.registrationName.isValidName()) {
                put("registrationName", "Full name must be at least 2 characters.")
            }
            if (!uiState.registrationEmail.isValidEmail()) {
                put("registrationEmail", "Enter a valid email address.")
            }
            if (!uiState.registrationPassword.isValidPassword()) {
                put("registrationPassword", "Password must be at least 6 characters.")
            }
        }
        return ValidationResult(
            fieldErrors = errors,
            message = errors.values.firstOrNull()
        )
    }

    private fun validateForgotPassword(): ValidationResult {
        val errors = buildMap<String, String> {
            if (!uiState.forgotPasswordEmail.isValidEmail()) {
                put("forgotPasswordEmail", "Enter a valid email address.")
            }
        }
        return ValidationResult(
            fieldErrors = errors,
            message = errors.values.firstOrNull()
        )
    }
}

private data class ValidationResult(
    val fieldErrors: Map<String, String>,
    val message: String?,
) {
    val hasErrors: Boolean get() = fieldErrors.isNotEmpty()
}
