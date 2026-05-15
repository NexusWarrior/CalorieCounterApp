package ru.example.caloriecounterapp.ui.components.diary

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.example.caloriecounterapp.R
import ru.example.caloriecounterapp.ui.theme.AccentLime
import ru.example.caloriecounterapp.ui.theme.DarkBackground
import ru.example.caloriecounterapp.ui.theme.TextSecondary

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = DarkBackground.copy(alpha = 0.95f),
        tonalElevation = 8.dp
    ) {
        val items = listOf("Дневник", "Рецепты", "Профиль", "Подписка")
        val icons = listOf(
            R.drawable.ic_dairy,
            R.drawable.ic_recipe,
            R.drawable.ic_profile,
            R.drawable.ic_subscription
        )

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == 0,
                onClick = { /* TODO */ },
                label = { Text(item, fontSize = 10.sp) },
                icon = {
                    Icon(
                        painterResource(icons[index]),
                        null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AccentLime,
                    selectedTextColor = AccentLime,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}