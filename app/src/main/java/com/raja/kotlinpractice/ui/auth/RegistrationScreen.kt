package com.raja.kotlinpractice.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raja.kotlinpractice.ui.theme.AppTextStyles
import com.raja.kotlinpractice.ui.viewmodel.AuthViewModel

@Composable
fun RegistrationSection(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.uiState

    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Registration", style = AppTextStyles.h4)
            OutlinedTextField(
                value = state.registrationName,
                onValueChange = viewModel::onRegistrationNameChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Full Name") },
                isError = state.registrationNameError != null,
                supportingText = {
                    state.registrationNameError?.let { Text(it) }
                }
            )
            OutlinedTextField(
                value = state.registrationEmail,
                onValueChange = viewModel::onRegistrationEmailChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                isError = state.registrationEmailError != null,
                supportingText = {
                    state.registrationEmailError?.let { Text(it) }
                }
            )
            OutlinedTextField(
                value = state.registrationPassword,
                onValueChange = viewModel::onRegistrationPasswordChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                isError = state.registrationPasswordError != null,
                supportingText = {
                    state.registrationPasswordError?.let { Text(it) }
                }
            )
            Button(
                onClick = viewModel::onRegistrationClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text(if (state.isLoading) "Creating..." else "Register")
            }
        }
    }
}
