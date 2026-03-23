package com.raja.kotlinpractice

import androidx.lifecycle.ViewModelProvider
import com.raja.kotlinpractice.ui.viewmodel.AppViewModelFactory
import io.mockk.mockk
import org.junit.Assert.assertSame
import org.junit.Test

class AppDependenciesTest {
    @Test
    fun provideViewModelFactory_returnsInjectedFactory() {
        val factory = mockk<AppViewModelFactory>(relaxed = true)
        val dependencies = AppDependencies(
            viewModelFactory = factory,
        )

        val exposedFactory: ViewModelProvider.Factory = dependencies.provideViewModelFactory()

        assertSame(factory, exposedFactory)
    }
}
