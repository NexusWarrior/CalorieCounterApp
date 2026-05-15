package ru.example.caloriecounterapp.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.example.caloriecounterapp.ui.theme.AccentLime
import ru.example.caloriecounterapp.ui.theme.SurfaceColor
import ru.example.caloriecounterapp.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = labelText, fontSize = 12.sp) },
            leadingIcon = icon,
            trailingIcon = trailingIcon,
            readOnly = readOnly,
            isError = isError, // Включаем режим ошибки
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                // Стандартные цвета
                focusedContainerColor = SurfaceColor,
                unfocusedContainerColor = SurfaceColor,
                focusedBorderColor = AccentLime,
                unfocusedBorderColor = Color.Transparent,
                focusedLabelColor = AccentLime,
                unfocusedLabelColor = TextSecondary,
                focusedLeadingIconColor = AccentLime,
                unfocusedLeadingIconColor = TextSecondary,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = AccentLime,

                // Цвета при ошибке
                errorContainerColor = SurfaceColor,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red,
                errorTrailingIconColor = Color.Red,
                errorCursorColor = Color.Red,
                errorTextColor = Color.White
            ),
            singleLine = true,
            visualTransformation = visualTransformation
                ?: if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )

        // Отрисовка текста ошибки под полем
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}