package com.raja.kotlinpractice.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raja.kotlinpractice.data.repository.AccountRepository
import com.raja.kotlinpractice.data.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppViewModelFactory @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel() as T

            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(authRepository) as T

            modelClass.isAssignableFrom(AccountViewModel::class.java) ->
                AccountViewModel(accountRepository) as T

            else -> error("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
