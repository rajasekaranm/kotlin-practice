package com.raja.kotlinpractice.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.raja.kotlinpractice.data.repository.AccountRepository

data class AccountUiState(
    val title: String = "Account",
    val actions: List<String> = emptyList(),
)

class AccountViewModel(
    private val accountRepository: AccountRepository,
) : ViewModel() {
    val uiState = AccountUiState(
        actions = listOf(
            "Get account",
            "Change password",
            "Delete account",
            "Update account",
            "Upload profile picture",
        )
    )

    fun repositoryName(): String = accountRepository::class.java.simpleName
}
