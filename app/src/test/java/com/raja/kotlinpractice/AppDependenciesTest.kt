package com.raja.kotlinpractice

import com.raja.kotlinpractice.data.local.SettingsRepository
import com.raja.kotlinpractice.data.remote.PracticeApiService
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertTrue
import org.junit.Test

class AppDependenciesTest {
    @Test
    fun summary_lists_added_libraries() {
        val dependencies = AppDependencies(
            settingsRepository = mockk<SettingsRepository>(),
            apiService = mockk<PracticeApiService>(),
            ioDispatcher = Dispatchers.Unconfined,
        )

        val summary = dependencies.summary()

        assertTrue(summary.contains("Dagger 2"))
        assertTrue(summary.contains("DataStore"))
        assertTrue(summary.contains("Retrofit"))
        assertTrue(summary.contains("Kotlin Coroutines"))
        assertTrue(summary.contains("MockK"))
    }
}
