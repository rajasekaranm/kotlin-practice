package com.raja.kotlinpractice.data.remote

import com.raja.kotlinpractice.data.local.SettingsRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenProvider @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    fun getGuestToken(): String = runBlocking {
        settingsRepository.getGuestToken().orEmpty()
    }

    fun getAccessToken(): String = runBlocking {
        settingsRepository.getAccessToken().orEmpty()
    }

    fun refreshAccessToken(): String {
        val currentRefreshToken = runBlocking { settingsRepository.getRefreshToken() }.orEmpty()
        val refreshedAccessToken = "$currentRefreshToken-refreshed"

        runBlocking {
            settingsRepository.saveAuthTokens(
                accessToken = refreshedAccessToken,
                refreshToken = currentRefreshToken.ifBlank { null },
            )
        }

        return refreshedAccessToken
    }
}
