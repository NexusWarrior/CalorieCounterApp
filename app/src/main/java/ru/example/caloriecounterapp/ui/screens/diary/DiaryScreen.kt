package ru.example.caloriecounterapp.ui.screens.diary


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.example.caloriecounterapp.ui.components.diary.BottomNavigationBar
import ru.example.caloriecounterapp.ui.components.diary.CalorieStatsCard
import ru.example.caloriecounterapp.ui.components.diary.HeaderSection
import ru.example.caloriecounterapp.ui.components.diary.MealListSection
import ru.example.caloriecounterapp.ui.components.diary.QuickAddSection
import ru.example.caloriecounterapp.ui.theme.DarkBackground


@Composable
fun DiaryScreen(onNavigateToScanner: () -> Unit) {
    Scaffold(
        containerColor = DarkBackground,
        bottomBar = { BottomNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection()

            CalorieStatsCard()

            Spacer(Modifier.height(24.dp))

            QuickAddSection(onScannerClick = onNavigateToScanner)

            Spacer(Modifier.height(24.dp))

            MealListSection()

            Spacer(Modifier.height(80.dp))
        }
    }
}

@Preview
@Composable
fun DiaryScreenPreview() {
    DiaryScreen(onNavigateToScanner = {})
}