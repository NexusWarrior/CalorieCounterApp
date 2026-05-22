package ru.example.caloriecounterapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.example.caloriecounterapp.R
import ru.example.caloriecounterapp.ui.components.auth.CustomTextField
import ru.example.caloriecounterapp.ui.theme.AccentLime
import ru.example.caloriecounterapp.ui.theme.DarkBackground
import ru.example.caloriecounterapp.ui.theme.TextSecondary

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    onNavigateToRegistration: () -> Unit,
    onSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = viewModel.authState.collectAsState().value

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onSuccess()
            viewModel.resetState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "С возвращением!",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.resetError()
            },
            labelText = "Email",
            keyboardType = KeyboardType.Email,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_email),
                    contentDescription = null,
                    tint = AccentLime,
                    modifier = Modifier.size(15.dp, 12.dp)
                )
            },
            isError = authState is AuthState.Error &&
                    authState.field == ErrorField.EMAIL,
            errorMessage = if (
                authState is AuthState.Error &&
                authState.field == ErrorField.EMAIL
            ) authState.message else null
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.resetError()
            },
            labelText = "Пароль",
            isPassword = true,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = null,
                    tint = AccentLime,
                    modifier = Modifier.size(19.dp)
                )
            },
            isError = authState is AuthState.Error &&
                    authState.field == ErrorField.PASSWORD,
            errorMessage = if (
                authState is AuthState.Error &&
                authState.field == ErrorField.PASSWORD
            ) authState.message else null
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (authState is AuthState.Error &&
            authState.field == ErrorField.NONE
        ) {
            Text(
                text = authState.message,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { viewModel.login(email, password) },
            enabled = authState !is AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentLime
            )
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "ВОЙТИ",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ещё нет аккаунта? ",
                color = TextSecondary,
                fontSize = 14.sp
            )

            Text(
                text = "Зарегистрироваться",
                color = AccentLime,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onNavigateToRegistration()
                }
            )
        }
    }
}