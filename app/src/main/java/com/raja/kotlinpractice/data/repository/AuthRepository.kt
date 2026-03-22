package com.raja.kotlinpractice.data.repository

import com.raja.kotlinpractice.data.local.SettingsRepository
import com.raja.kotlinpractice.data.remote.AuthApiService
import com.raja.kotlinpractice.data.remote.model.AuthMessageResponse
import com.raja.kotlinpractice.data.remote.model.AuthTokenResponse
import com.raja.kotlinpractice.data.remote.model.GuestAccessTokenRequest
import com.raja.kotlinpractice.data.remote.model.LoginRequest
import com.raja.kotlinpractice.data.remote.model.RegistrationRequest
import com.raja.kotlinpractice.data.remote.model.ResetPasswordRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val settingsRepository: SettingsRepository,
) {
    suspend fun login(email: String, password: String): AuthTokenResponse {
        val response = authApiService.login(LoginRequest(email = email, password = password))
        settingsRepository.saveAuthTokens(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
        )
        return response
    }

    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String? = null,
    ): AuthMessageResponse = authApiService.register(
        RegistrationRequest(
            fullName = fullName,
            email = email,
            password = password,
            phoneNumber = phoneNumber,
        )
    )

    suspend fun resetPassword(email: String): AuthMessageResponse =
        authApiService.resetPassword(ResetPasswordRequest(email = email))

    suspend fun guestAccessToken(deviceId: String, platform: String): AuthTokenResponse {
        val response = authApiService.guestAccessToken(
            GuestAccessTokenRequest(
                deviceId = deviceId,
                platform = platform,
            )
        )
        settingsRepository.saveGuestToken(response.accessToken)
        return response
    }
}
