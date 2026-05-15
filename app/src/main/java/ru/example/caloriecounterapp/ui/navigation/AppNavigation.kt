package ru.example.caloriecounterapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.example.caloriecounterapp.ui.screens.auth.RegistrationScreen
import ru.example.caloriecounterapp.ui.screens.diary.DiaryScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "registration" // Начальный экран
    ) {
        // Экран регистрации
        composable("registration") {
            RegistrationScreen(
                onNavigateToLogin = {
                    // Будущие переходы
                },
                onSuccess = {
                    // Если регистрация прошла успешно, летим в Дневник
                    navController.navigate("diary") {
                        popUpTo("registration") {
                            inclusive = true
                        } // удаляет экран регистрации из истории
                    }
                }
            )
        }

        // Экран дневника
        composable("diary") {
            DiaryScreen(
                onNavigateToScanner = {
                    // navController.navigate("scanner") Переход на сканнер - пока заглушка
                }
            )
        }
    }
}