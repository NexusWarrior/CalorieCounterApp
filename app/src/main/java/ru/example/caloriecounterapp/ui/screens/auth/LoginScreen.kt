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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.example.caloriecounterapp.ui.components.auth.CustomTextField
import ru.example.caloriecounterapp.ui.theme.AccentLime
import ru.example.caloriecounterapp.ui.theme.DarkBackground
import ru.example.caloriecounterapp.ui.theme.TextSecondary

@Composable
fun LoginScreen(
    // viewModel: AuthViewModel,
    onNavigateToRegistration: () -> Unit,
    onSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "С возвращением!",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Поле ввода Email
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            labelText = "Email",
            keyboardType = KeyboardType.Email,
            icon = { /* Передай иконку email, если есть */ }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Поле ввода Пароля
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            labelText = "Пароль",
            keyboardType = KeyboardType.Password,
            icon = { /* Передай иконку пароля, если есть */ }
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка входа
        Button(
            onClick = {
                // TODO: Вызов метода логина через Firebase, например:
                // viewModel.login(email, password)
                onSuccess() // Временно переходим сразу в дневник для проверки
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentLime)
        ) {
            Text("ВОЙТИ", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка перехода обратно к регистрации
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
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
                modifier = Modifier
                    .clickable { onNavigateToRegistration() }
                    .padding(4.dp)
            )
        }
    }
}