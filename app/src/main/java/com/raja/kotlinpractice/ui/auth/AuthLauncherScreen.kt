package com.raja.kotlinpractice.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raja.kotlinpractice.ui.theme.AppTextStyles
import com.raja.kotlinpractice.ui.viewmodel.AuthRoute
import com.raja.kotlinpractice.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthLauncherScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.uiState

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (state.currentRoute != AuthRoute.LOGIN) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = when (state.currentRoute) {
                                AuthRoute.REGISTRATION -> "Registration"
                                AuthRoute.FORGOT_PASSWORD -> "Forget Password"
                                AuthRoute.LOGIN -> ""
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = viewModel::openLogin) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 420.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (state.currentRoute) {
                    AuthRoute.LOGIN -> LoginSection(viewModel = viewModel)
                    AuthRoute.REGISTRATION -> RegistrationSection(viewModel = viewModel)
                    AuthRoute.FORGOT_PASSWORD -> ForgetPasswordSection(viewModel = viewModel)
                }

                state.errorMessage?.let { message ->
                    Text(
                        text = message,
                        style = AppTextStyles.bodyMedium,
                        color = Color(0xFFB3261E),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                state.infoMessage?.let { message ->
                    Text(
                        text = message,
                        style = AppTextStyles.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                if (state.isLoading) {
                    Text(
                        text = "Loading...",
                        style = AppTextStyles.bodySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = state.appInfo,
                    style = AppTextStyles.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
