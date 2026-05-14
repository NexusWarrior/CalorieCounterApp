package ru.example.caloriecounterapp.ui.screens.auth

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.graphics.ColorFilter
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
import ru.example.caloriecounterapp.ui.theme.SurfaceColor
import ru.example.caloriecounterapp.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
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

    // Состояния UI (Календарь и Меню)
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var isGenderMenuExpanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("Мужской", "Женский")

    val authState by viewModel.authState.collectAsState()

    // Слушаем успех регистрации
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

        // Имя
        CustomTextField(
            value = name,
            onValueChange = { name = it },
            labelText = "Имя",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_user),
                    contentDescription = null,
                    tint = AccentLime,
                    modifier = Modifier.size(19.dp)
                )
            }
        )
        Spacer(Modifier.height(16.dp))

        // Почта
        CustomTextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.resetError()
            },
            labelText = "Почта",
            icon = {
                Icon(
                    painterResource(R.drawable.ic_email),
                    null,
                    tint = AccentLime,
                    modifier = Modifier.size(15.dp, 12.dp)
                )
            },
            keyboardType = KeyboardType.Email,
            isError = authState is AuthState.Error && (authState as AuthState.Error).field == ErrorField.EMAIL,
            errorMessage = if (authState is AuthState.Error && (authState as AuthState.Error).field == ErrorField.EMAIL) (authState as AuthState.Error).message else null
        )
        Spacer(Modifier.height(16.dp))

        // Пароль
        CustomTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.resetError()
            },
            labelText = "Пароль",
            icon = {
                Icon(
                    painterResource(R.drawable.ic_lock),
                    null,
                    tint = AccentLime,
                    modifier = Modifier.size(19.dp)
                )
            },
            isPassword = true,
            isError = authState is AuthState.Error && (authState as AuthState.Error).field == ErrorField.PASSWORD,
            errorMessage = if (authState is AuthState.Error && (authState as AuthState.Error).field == ErrorField.PASSWORD) (authState as AuthState.Error).message else null
        )
        Spacer(Modifier.height(16.dp))

        // Повторный пароль
        CustomTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                viewModel.resetError()
            },
            labelText = "Повторите пароль",
            icon = {
                Icon(
                    painterResource(R.drawable.ic_lock),
                    null,
                    tint = AccentLime,
                    modifier = Modifier.size(19.dp)
                )
            },
            isPassword = true,
            isError = authState is AuthState.Error && (authState as AuthState.Error).field == ErrorField.CONFIRM_PASSWORD,
            errorMessage = if (authState is AuthState.Error && (authState as AuthState.Error).field == ErrorField.CONFIRM_PASSWORD) (authState as AuthState.Error).message else null
        )
        Spacer(Modifier.height(16.dp))

        // Дата рождения
        Box(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                value = birthDate,
                onValueChange = {},
                labelText = "Дата рождения",
                readOnly = true,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = AccentLime,
                        modifier = Modifier.size(19.dp)
                    )
                }
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { showDatePicker = true })
        }
        Spacer(Modifier.height(16.dp))

        // Пол
        ExposedDropdownMenuBox(
            expanded = isGenderMenuExpanded,
            onExpandedChange = { isGenderMenuExpanded = !isGenderMenuExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomTextField(
                value = gender,
                onValueChange = {},
                labelText = "Пол",
                readOnly = true,
                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_people),
                        contentDescription = null,
                        tint = AccentLime,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderMenuExpanded)
                }
            )

            ExposedDropdownMenu(
                expanded = isGenderMenuExpanded,
                onDismissRequest = { isGenderMenuExpanded = false },
                modifier = Modifier.background(SurfaceColor)
            ) {
                genderOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, color = Color.White) },
                        onClick = {
                            gender = option
                            isGenderMenuExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))

        // Checkbox
        val isTermsError =
            authState is AuthState.Error && (authState as AuthState.Error).field == ErrorField.TERMS
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isTermsAccepted,
                    onCheckedChange = {
                        isTermsAccepted = it
                        viewModel.resetError()
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = AccentLime,
                        uncheckedColor = if (isTermsError) Color.Red else TextSecondary,
                        checkmarkColor = Color.Black
                    )
                )
                // Пользовательское соглашение
                val annotatedString = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = TextSecondary)) { append("Я принимаю условия ") }
                    withStyle(style = SpanStyle(color = AccentLime)) { append("Пользовательского соглашения") }
                    withStyle(style = SpanStyle(color = TextSecondary)) { append(" и ") }
                    withStyle(style = SpanStyle(color = AccentLime)) { append("Политики конфиденциальности") }
                }
                Text(
                    text = annotatedString,
                    fontSize = 14.sp,
                )
            }

            // Вывод текста ошибки именно для чекбокса
            if (isTermsError) {
                Text(
                    text = (authState as AuthState.Error).message,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Глобальные ошибки
        if (authState is AuthState.Error && (authState as AuthState.Error).field == ErrorField.NONE) {
            Text(
                text = (authState as AuthState.Error).message,
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

        // Регистрация через Google, Apple
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 24.dp)
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.DarkGray)
            Text(" или ", color = TextSecondary, modifier = Modifier.padding(horizontal = 8.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.DarkGray)
        }

        SocialButton(
            text = "Продолжить с Google",
            iconRes = R.drawable.ic_google,
            keepOriginalColor = true,
            onClick = { /* TODO: Google Auth */ }
        )
        Spacer(Modifier.height(12.dp))
        SocialButton(
            text = "Продолжить с Apple",
            iconRes = R.drawable.ic_apple,
            keepOriginalColor = false,
            onClick = { /* TODO: Apple Auth */ }
        )

        Spacer(Modifier.height(32.dp))

        Row {
            Text("Уже есть аккаунт? ", color = TextSecondary)
            Text(
                "Войти",
                color = AccentLime,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToLogin() }
            )
        }
        Spacer(Modifier.height(24.dp))
    }

    // Встроенный календарь
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        birthDate = sdf.format(Date(millis))
                    }
                    showDatePicker = false
                }) { Text("ОК", color = AccentLime) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Отмена", color = TextSecondary)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

// Кастомная кнопка
@Composable
fun SocialButton(
    text: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    keepOriginalColor: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SurfaceColor,
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = text, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = if (keepOriginalColor) null else ColorFilter.tint(Color.White)
            )
        }
    }
}