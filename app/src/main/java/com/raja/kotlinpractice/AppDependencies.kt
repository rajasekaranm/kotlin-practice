package com.raja.kotlinpractice

import com.raja.kotlinpractice.data.local.SettingsRepository
import com.raja.kotlinpractice.data.remote.PracticeApiService
import com.raja.kotlinpractice.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AppDependencies @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val apiService: PracticeApiService,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    fun summary(): String = buildString {
        appendLine("Configured libraries:")
        appendLine("- Dagger 2")
        appendLine("- DataStore")
        appendLine("- Retrofit")
        appendLine("- Kotlin Coroutines")
        appendLine("- MockK (test)")
        appendLine()
        appendLine("Repository: ${settingsRepository::class.java.simpleName}")
        appendLine("Retrofit service: ${apiService::class.java.simpleName}")
        append("IO dispatcher: $ioDispatcher")
    }
}
