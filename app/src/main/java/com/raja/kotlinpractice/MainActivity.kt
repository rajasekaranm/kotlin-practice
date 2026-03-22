package com.raja.kotlinpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raja.kotlinpractice.ui.auth.AuthLauncherScreen
import com.raja.kotlinpractice.ui.screens.AccountScreen
import com.raja.kotlinpractice.ui.screens.FavouriteScreen
import com.raja.kotlinpractice.ui.screens.HomeScreen
import com.raja.kotlinpractice.ui.viewmodel.AccountViewModel
import com.raja.kotlinpractice.ui.viewmodel.AuthViewModel
import com.raja.kotlinpractice.ui.viewmodel.FavouriteViewModel
import com.raja.kotlinpractice.ui.viewmodel.HomeViewModel
import com.raja.kotlinpractice.ui.theme.KotlinPracticeTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appDependencies: AppDependencies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as KotlinPracticeApplication).appComponent.inject(this)
        enableEdgeToEdge()
        setContent {
            KotlinPracticeTheme {
                KotlinPracticeApp(appDependencies.provideViewModelFactory())
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun KotlinPracticeApp(
    viewModelFactory: ViewModelProvider.Factory? = null,
) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val factory = viewModelFactory ?: PreviewViewModelFactory()
    val authViewModel: AuthViewModel = viewModel(factory = factory)
    val isAuthenticated = authViewModel.uiState.isAuthenticated

    if (!isAuthenticated) {
        AuthLauncherScreen(
            viewModel = authViewModel,
            modifier = Modifier.fillMaxSize()
        )
        return
    }

    val homeViewModel: HomeViewModel = viewModel(factory = factory)
    val favouriteViewModel: FavouriteViewModel = viewModel(factory = factory)
    val accountViewModel: AccountViewModel = viewModel(factory = factory)

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(
                    uiState = homeViewModel.uiState,
                    modifier = Modifier.padding(innerPadding)
                )

                AppDestinations.FAVOURITE -> FavouriteScreen(
                    uiState = favouriteViewModel.uiState,
                    modifier = Modifier.padding(innerPadding)
                )

                AppDestinations.ACCOUNT -> AccountScreen(
                    uiState = accountViewModel.uiState,
                    repositoryName = accountViewModel.repositoryName(),
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    FAVOURITE("Favourite", Icons.Default.Favorite),
    ACCOUNT("Account", Icons.Default.AccountBox),
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinPracticeTheme {
        KotlinPracticeApp()
    }
}

private class PreviewViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel() as T

            modelClass.isAssignableFrom(FavouriteViewModel::class.java) ->
                FavouriteViewModel() as T

            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(
                    authRepository = com.raja.kotlinpractice.data.repository.AuthRepository(
                        authApiService = object : com.raja.kotlinpractice.data.remote.AuthApiService {
                            override suspend fun login(request: com.raja.kotlinpractice.data.remote.model.LoginRequest) =
                                com.raja.kotlinpractice.data.remote.model.AuthTokenResponse("preview-token")

                            override suspend fun register(request: com.raja.kotlinpractice.data.remote.model.RegistrationRequest) =
                                com.raja.kotlinpractice.data.remote.model.AuthMessageResponse("preview")

                            override suspend fun resetPassword(request: com.raja.kotlinpractice.data.remote.model.ResetPasswordRequest) =
                                com.raja.kotlinpractice.data.remote.model.AuthMessageResponse("preview")

                            override suspend fun guestAccessToken(request: com.raja.kotlinpractice.data.remote.model.GuestAccessTokenRequest) =
                                com.raja.kotlinpractice.data.remote.model.AuthTokenResponse("preview-guest")
                        },
                        settingsRepository = previewSettingsRepository()
                    )
                ) as T

            modelClass.isAssignableFrom(AccountViewModel::class.java) ->
                AccountViewModel(
                    accountRepository = com.raja.kotlinpractice.data.repository.AccountRepository(
                        accountApiService = object : com.raja.kotlinpractice.data.remote.AccountApiService {
                            override suspend fun getAccount() =
                                com.raja.kotlinpractice.data.remote.model.AccountResponse(
                                    id = "preview-id",
                                    fullName = "Preview User",
                                    email = "preview@example.com"
                                )

                            override suspend fun changePassword(request: com.raja.kotlinpractice.data.remote.model.ChangePasswordRequest) =
                                com.raja.kotlinpractice.data.remote.model.AuthMessageResponse("preview")

                            override suspend fun deleteAccount() =
                                com.raja.kotlinpractice.data.remote.model.AuthMessageResponse("preview")

                            override suspend fun updateAccount(request: com.raja.kotlinpractice.data.remote.model.UpdateAccountRequest) =
                                com.raja.kotlinpractice.data.remote.model.AccountResponse(
                                    id = "preview-id",
                                    fullName = request.fullName,
                                    email = "preview@example.com"
                                )

                            override suspend fun uploadProfilePicture(profilePicture: okhttp3.MultipartBody.Part) =
                                com.raja.kotlinpractice.data.remote.model.AccountResponse(
                                    id = "preview-id",
                                    fullName = "Preview User",
                                    email = "preview@example.com"
                                )
                        }
                    )
                ) as T

            else -> error("Unknown ViewModel: ${modelClass.name}")
        }
    }

    private fun previewSettingsRepository(): com.raja.kotlinpractice.data.local.SettingsRepository =
        com.raja.kotlinpractice.data.local.SettingsRepository(
            dataStore = object : androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> {
                override val data =
                    kotlinx.coroutines.flow.flowOf(androidx.datastore.preferences.core.emptyPreferences())

                override suspend fun updateData(
                    transform: suspend (t: androidx.datastore.preferences.core.Preferences) -> androidx.datastore.preferences.core.Preferences
                ): androidx.datastore.preferences.core.Preferences =
                    transform(androidx.datastore.preferences.core.emptyPreferences())
            },
            ioDispatcher = kotlinx.coroutines.Dispatchers.Unconfined
        )
}
