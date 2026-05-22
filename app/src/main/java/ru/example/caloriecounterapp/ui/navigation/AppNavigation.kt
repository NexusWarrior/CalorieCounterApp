package ru.example.caloriecounterapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.example.caloriecounterapp.ui.screens.auth.LoginScreen
import ru.example.caloriecounterapp.ui.screens.auth.RegistrationScreen
import ru.example.caloriecounterapp.ui.screens.diary.DiaryScreen
import ru.example.caloriecounterapp.ui.screens.product_detail.ProductDetailScreen
import ru.example.caloriecounterapp.ui.screens.product_detail.ProductDetailViewModel
import ru.example.caloriecounterapp.ui.screens.scanner.ScannerScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "registration"
    ) {
        // 1. Регистрация
        composable("registration") {
            RegistrationScreen(
                onNavigateToLogin = {
                    navController.navigate("login")
                },
                onSuccess = {
                    navController.navigate("diary") {
                        popUpTo("registration") { inclusive = true }
                    }
                }
            )
        }

        // 1.1. Вход в аккаунт (Логин)
        composable("login") {
            LoginScreen(
                onNavigateToRegistration = {
                    navController.popBackStack()
                },
                onSuccess = {
                    navController.navigate("diary") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 2. Дневник (Главный экран)
        composable("diary") {
            DiaryScreen(
                onNavigateToScanner = {
                    navController.navigate("scanner")
                }
            )
        }

        // 3. Сканер
        composable("scanner") {
            ScannerScreen(
                onCodeScanned = { barcode ->
                    navController.navigate("product_details/$barcode")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Детали продукта (с аргументом barcode)
        composable(
            route = "product_details/{barcode}",
            arguments = listOf(navArgument("barcode") { type = NavType.StringType })
        ) { backStackEntry ->
            val barcode = backStackEntry.arguments?.getString("barcode") ?: ""
            val viewModel: ProductDetailViewModel = viewModel()

            LaunchedEffect(barcode) {
                viewModel.fetchProduct(barcode)
            }

            ProductDetailScreen(
                viewModel = viewModel,
                onSave = {
                    navController.popBackStack("diary", inclusive = false)
                }
            )
        }
    }
}