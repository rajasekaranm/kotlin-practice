package com.raja.kotlinpractice

import androidx.lifecycle.ViewModelProvider
import com.raja.kotlinpractice.ui.viewmodel.AppViewModelFactory
import javax.inject.Inject

class AppDependencies @Inject constructor(
    val viewModelFactory: AppViewModelFactory,
) {
    fun provideViewModelFactory(): ViewModelProvider.Factory = viewModelFactory
}
