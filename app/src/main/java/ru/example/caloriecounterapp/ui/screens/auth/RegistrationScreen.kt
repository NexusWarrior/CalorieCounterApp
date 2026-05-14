package ru.example.caloriecounterapp.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.example.caloriecounterapp.R
import ru.example.caloriecounterapp.ui.components.CustomTextField
import ru.example.caloriecounterapp.ui.theme.AccentLime
import ru.example.caloriecounterapp.ui.theme.DarkBackground
import ru.example.caloriecounterapp.ui.theme.TextSecondary

@Composable
fun RegistrationScreen(
    viewModel: AuthViewModel = viewModel(),
    onNavigateToLogin: () -> Unit,
    onSuccess: () -> Unit
) {
    // Состояния ввода
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Мужской") }
    var isTermsAccepted by remember { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()

    // Успех регистрации
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) onSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Регистрация",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(top = 40.dp, bottom = 24.dp)
                .align(Alignment.Start)
        )

        // Поля ввода
        CustomTextField(
            value = name,
            onValueChange = { name = it },
            labelText = "Имя",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_user),
                    contentDescription = null,
                    tint = AccentLime
                )
            },
        )
        Spacer(Modifier.height(16.dp))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            labelText = "Почта",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_email),
                    contentDescription = null,
                    tint = AccentLime
                )
            },
            keyboardType = KeyboardType.Email
        )
        Spacer(Modifier.height(16.dp))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            labelText = "Пароль",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = null,
                    tint = AccentLime
                )
            },
            isPassword = true
        )
        Spacer(Modifier.height(16.dp))

        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            labelText = "Повторите пароль",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = null,
                    tint = AccentLime
                )
            },
            isPassword = true
        )
        Spacer(Modifier.height(16.dp))

        // Дата рождения (пока просто поле)
        CustomTextField(
            value = birthDate,
            onValueChange = { birthDate = it },
            labelText = "Дата рождения",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = AccentLime
                )
            }
        )
        Spacer(Modifier.height(16.dp))

        // Пол (пока просто поле)
        CustomTextField(
            value = gender,
            onValueChange = { gender = it },
            labelText = "Пол",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_people),
                    contentDescription = null,
                    tint = AccentLime
                )
            },
        )

        Spacer(Modifier.height(24.dp))

        // Чекбокс
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Checkbox(
                checked = isTermsAccepted,
                onCheckedChange = { isTermsAccepted = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = AccentLime,
                    uncheckedColor = TextSecondary,
                    checkmarkColor = Color.Black
                )
            )
            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = TextSecondary)) {
                    append("Я принимаю условия ")
                }
                withStyle(style = SpanStyle(color = AccentLime)) {
                    append("Пользовательского соглашения")
                }
                withStyle(style = SpanStyle(color = TextSecondary)) {
                    append(" и ")
                }
                withStyle(style = SpanStyle(color = AccentLime)) {
                    append("Политики конфиденциальности")
                }
            }

            Text(
                text = annotatedString,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(Modifier.height(32.dp))

        // Ошибки
        if (authState is AuthState.Error) {
            Text(
                (authState as AuthState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Кнопка регистрации
        if (authState is AuthState.Loading) {
            CircularProgressIndicator(color = AccentLime)
        } else {
            Button(
                onClick = { viewModel.register(email, password, confirmPassword, isTermsAccepted) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentLime,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Зарегистрироваться",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        // Разделитель
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 24.dp)
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.DarkGray)
            Text(" или ", color = TextSecondary, modifier = Modifier.padding(horizontal = 8.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.DarkGray)
        }

        // Соцсети (заглушки)
        SocialButton("Продолжить с Google")
        Spacer(Modifier.height(12.dp))
        SocialButton("Продолжить с Apple")

        Spacer(Modifier.height(32.dp))

        // Переход на вход (пока просто текст)
        Row {
            Text("Уже есть аккаунт? ", color = TextSecondary)
            Text(
                "Войти",
                color = AccentLime,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToLogin() })
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun SocialButton(text: String) {
    OutlinedButton(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
        border = BorderStroke(1.dp, Color.DarkGray)
    ) {
        Text(text)
    }
}