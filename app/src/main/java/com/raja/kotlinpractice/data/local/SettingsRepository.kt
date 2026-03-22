package com.raja.kotlinpractice.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.raja.kotlinpractice.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun dataStoreName(): String = withContext(ioDispatcher) {
        dataStore.toString()
    }
}
