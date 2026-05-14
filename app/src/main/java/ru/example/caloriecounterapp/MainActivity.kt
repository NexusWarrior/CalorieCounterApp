package ru.example.caloriecounterapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.example.caloriecounterapp.ui.screens.auth.RegistrationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrationScreen(
                onNavigateToLogin = {
                    Log.d("NAV", "Переход на экран логина")
                },
                onSuccess = {
                    Log.d("NAV", "Регистрация прошла успешно! Переход в приложение")
                }
            )
        }
    }
}