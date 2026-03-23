package com.raja.kotlinpractice.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.raja.kotlinpractice.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getGuestToken(): String? = withContext(ioDispatcher) {
        dataStore.data.firstOrNull()?.get(GUEST_TOKEN)
    }

    suspend fun saveGuestToken(token: String) = withContext(ioDispatcher) {
        dataStore.edit { preferences ->
            preferences[GUEST_TOKEN] = token
        }
    }

    suspend fun getAccessToken(): String? = withContext(ioDispatcher) {
        dataStore.data.firstOrNull()?.get(ACCESS_TOKEN)
    }

    suspend fun getRefreshToken(): String? = withContext(ioDispatcher) {
        dataStore.data.firstOrNull()?.get(REFRESH_TOKEN)
    }

    suspend fun saveAuthTokens(
        accessToken: String,
        refreshToken: String?,
    ) = withContext(ioDispatcher) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
            if (refreshToken.isNullOrBlank()) {
                preferences.remove(REFRESH_TOKEN)
            } else {
                preferences[REFRESH_TOKEN] = refreshToken
            }
        }
    }

    suspend fun clearAuthTokens() = withContext(ioDispatcher) {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(REFRESH_TOKEN)
        }
    }

    suspend fun dataStoreName(): String = withContext(ioDispatcher) {
        dataStore.toString()
    }

    private companion object {
        val GUEST_TOKEN = stringPreferencesKey("guest_token")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}
