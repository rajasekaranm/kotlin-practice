package com.raja.kotlinpractice.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raja.kotlinpractice.ui.viewmodel.AuthViewModel

@Composable
fun LoginSection(
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
            Text(
                text = "Login",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = state.loginEmail,
                onValueChange = viewModel::onLoginEmailChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") }
            )
            OutlinedTextField(
                value = state.loginPassword,
                onValueChange = viewModel::onLoginPasswordChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = viewModel::openForgotPassword) {
                    Text("Forget Password?")
                }
            }
            TextButton(
                onClick = viewModel::openRegistration,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Account")
            }
            Button(onClick = viewModel::onLoginClick, modifier = Modifier.fillMaxWidth()) {
                Text("Login")
            }
        }
    }
}
