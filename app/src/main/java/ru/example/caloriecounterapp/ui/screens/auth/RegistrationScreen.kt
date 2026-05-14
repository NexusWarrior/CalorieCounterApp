package ru.example.caloriecounterapp.ui.screens.auth

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
    // --- Состояния ввода ---
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Мужской") }
    var isTermsAccepted by remember { mutableStateOf(false) }

    // --- Состояния UI (Календарь и Меню) ---
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
            onValueChange = { email = it },
            labelText = "Почта",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_email),
                    contentDescription = null,
                    tint = AccentLime,
                    modifier = Modifier.size(width = 15.dp, height = 12.dp)
                )
            },
            keyboardType = KeyboardType.Email
        )
        Spacer(Modifier.height(16.dp))

        // Пароль
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            labelText = "Пароль",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = null,
                    tint = AccentLime,
                    modifier = Modifier.size(19.dp)
                )
            },
            isPassword = true
        )
        Spacer(Modifier.height(16.dp))

        // Повтор пароля
        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            labelText = "Повторите пароль",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = null,
                    tint = AccentLime,
                    modifier = Modifier.size(19.dp)
                )
            },
            isPassword = true
        )
        Spacer(Modifier.height(16.dp))

        // --- ДАТА РОЖДЕНИЯ (Кликабельное поле) ---
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
                modifier = Modifier.background(SurfaceColor) // Твой цвет подложки
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

        // Чекбокс Условий
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
                withStyle(style = SpanStyle(color = TextSecondary)) { append("Я принимаю условия ") }
                withStyle(style = SpanStyle(color = AccentLime)) { append("Соглашения") }
                withStyle(style = SpanStyle(color = TextSecondary)) { append(" и ") }
                withStyle(style = SpanStyle(color = AccentLime)) { append("Политики") }
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
                Text(text = "Зарегистрироваться", fontWeight = FontWeight.Bold, fontSize = 16.sp)
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

        // Соцсети
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
            keepOriginalColor = false, // Перекрашиваем Apple в белый
            onClick = { /* TODO: Apple Auth */ }
        )

        Spacer(Modifier.height(32.dp))

        // Переход на логин
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

    // --- Календарь (Диалог) ---
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
                    Text(
                        "Отмена",
                        color = TextSecondary
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

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