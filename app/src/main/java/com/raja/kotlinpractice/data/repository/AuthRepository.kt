package com.raja.kotlinpractice.data.repository

import com.raja.kotlinpractice.data.local.SettingsRepository
import com.raja.kotlinpractice.data.remote.ApiErrorHandler
import com.raja.kotlinpractice.data.remote.ApiResult
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
    private val apiErrorHandler: ApiErrorHandler,
) {
    suspend fun login(email: String, password: String): ApiResult<AuthTokenResponse> =
        apiErrorHandler.safeApiCall {
            authApiService.login(LoginRequest(email = email, password = password)).also { response ->
                settingsRepository.saveAuthTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                )
            }
        }

    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String? = null,
    ): ApiResult<AuthMessageResponse> =
        apiErrorHandler.safeApiCall {
            authApiService.register(
                RegistrationRequest(
                    fullName = fullName,
                    email = email,
                    password = password,
                    phoneNumber = phoneNumber,
                )
            )
        }

    suspend fun resetPassword(email: String): ApiResult<AuthMessageResponse> =
        apiErrorHandler.safeApiCall {
            authApiService.resetPassword(ResetPasswordRequest(email = email))
        }

    suspend fun guestAccessToken(deviceId: String, platform: String): ApiResult<AuthTokenResponse> =
        apiErrorHandler.safeApiCall {
            authApiService.guestAccessToken(
                GuestAccessTokenRequest(
                    deviceId = deviceId,
                    platform = platform,
                )
            ).also { response ->
                settingsRepository.saveGuestToken(response.accessToken)
            }
        }
}
