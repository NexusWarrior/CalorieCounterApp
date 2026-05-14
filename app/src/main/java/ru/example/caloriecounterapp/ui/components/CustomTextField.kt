package ru.example.caloriecounterapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.example.caloriecounterapp.R
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
    readOnly: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = labelText, fontSize = 12.sp) },
        leadingIcon = icon,
        trailingIcon = trailingIcon,
        readOnly = readOnly,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(

            // Контейнер
            focusedContainerColor = SurfaceColor,
            unfocusedContainerColor = SurfaceColor,

            // Контур
            focusedBorderColor = AccentLime,
            unfocusedBorderColor = Color.Transparent,

            // Label
            focusedLabelColor = AccentLime,
            unfocusedLabelColor = TextSecondary,

            // Иконка
            focusedLeadingIconColor = AccentLime,
            unfocusedLeadingIconColor = TextSecondary,

            // Текст
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,

            // Курсор
            cursorColor = AccentLime
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    var name by remember { mutableStateOf("") }

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
        }
    )
}