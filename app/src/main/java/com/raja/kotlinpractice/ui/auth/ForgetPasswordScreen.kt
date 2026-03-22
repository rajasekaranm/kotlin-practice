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
fun ForgetPasswordSection(
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
            Text(text = "Forget Password", style = AppTextStyles.h4)
            OutlinedTextField(
                value = state.forgotPasswordEmail,
                onValueChange = viewModel::onForgotPasswordEmailChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") }
            )
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Send Reset Link")
            }
        }
    }
}
