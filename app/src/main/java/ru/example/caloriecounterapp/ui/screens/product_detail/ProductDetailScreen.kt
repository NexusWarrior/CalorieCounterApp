package ru.example.caloriecounterapp.ui.screens.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    onSave: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        when (val currentState = state) {
            is ProductState.Loading -> {
                CircularProgressIndicator(
                    color = AccentLime,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is ProductState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(currentState.message, color = Color.Red, fontSize = 18.sp)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = onSave, colors = ButtonDefaults.buttonColors(AccentLime)) {
                        Text("Вернуться", color = Color.Black)
                    }
                }
            }

            is ProductState.Success -> {
                val product = currentState.product
                val nutriments = product.nutriments

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Имя продукта
                    Text(
                        text = product.product_name ?: "Неизвестный продукт",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(20.dp))

                    CustomTextField(
                        value = "100",
                        onValueChange = {},
                        labelText = "Вес (г)",
                        keyboardType = KeyboardType.Number,
                        icon = {})
                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = onSave,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(AccentLime)
                    ) {
                        Text("СОХРАНИТЬ", color = Color.Black, fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.height(30.dp))
                    Text("Питательная ценность на 100г", color = Color.White, fontSize = 18.sp)
                    Spacer(Modifier.height(16.dp))

                    // Макронутриенты
                    val details = listOf(
                        "Калории" to "${nutriments?.energy_kcal_100g ?: 0.0} ккал",
                        "Белки" to "${nutriments?.proteins_100g ?: 0.0} г",
                        "Жиры" to "${nutriments?.fat_100g ?: 0.0} г",
                        "Углеводы" to "${nutriments?.carbohydrates_100g ?: 0.0} г"
                    )

                    details.forEach { (label, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(label, color = TextSecondary)
                            Text(value, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        HorizontalDivider(color = Color.White.copy(0.1f))
                    }
                }
            }
        }
    }
}