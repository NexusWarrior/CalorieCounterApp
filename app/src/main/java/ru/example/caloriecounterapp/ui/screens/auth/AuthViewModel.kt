package ru.example.caloriecounterapp.ui.screens.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Перечисление полей, где может быть ошибка
enum class ErrorField {
    EMAIL, PASSWORD, CONFIRM_PASSWORD, TERMS, NONE
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()

    // Теперь ошибка несет в себе не только текст, но и указание на конкретное поле
    data class Error(val message: String, val field: ErrorField = ErrorField.NONE) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun register(email: String, pass: String, confirmPass: String, terms: Boolean) {
        val cleanEmail = email.replace("\\s+".toRegex(), "")
        val cleanPass = pass.trim()
        val cleanConfirmPass = confirmPass.trim()

        // === КЛИЕНТСКАЯ ВАЛИДАЦИЯ (До отправки на сервер) ===

        if (cleanEmail.isBlank()) {
            _authState.value = AuthState.Error("Введите почту", ErrorField.EMAIL)
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
            _authState.value = AuthState.Error("Неверный формат почты", ErrorField.EMAIL)
            return
        }
        if (cleanPass.length < 6) {
            _authState.value =
                AuthState.Error("Пароль должен содержать минимум 6 символов", ErrorField.PASSWORD)
            return
        }
        if (cleanPass != cleanConfirmPass) {
            _authState.value = AuthState.Error("Пароли не совпадают", ErrorField.CONFIRM_PASSWORD)
            return
        }
        if (!terms) {
            _authState.value =
                AuthState.Error("Примите пользовательское соглашение", ErrorField.TERMS)
            return
        }

        // Если все локальные проверки пройдены, стучимся в Firebase
        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(cleanEmail, cleanPass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    val (message, errorField) = getRussianErrorDetails(task.exception)
                    _authState.value = AuthState.Error(message, errorField)
                }
            }
    }

    // Возвращает пару: (Текст ошибки, Поле для подсветки)
    private fun getRussianErrorDetails(exception: Exception?): Pair<String, ErrorField> {
        return when (exception) {
            is FirebaseAuthWeakPasswordException ->
                Pair("Пароль слишком простой (минимум 6 символов).", ErrorField.PASSWORD)

            is FirebaseAuthInvalidCredentialsException ->
                Pair("Неверный формат почты.", ErrorField.EMAIL)

            is FirebaseAuthUserCollisionException ->
                Pair("Пользователь с такой почтой уже существует.", ErrorField.EMAIL)

            is FirebaseNetworkException ->
                Pair("Ошибка сети. Проверьте подключение.", ErrorField.NONE)

            else ->
                Pair("Произошла неизвестная ошибка. Попробуйте позже.", ErrorField.NONE)
        }
    }

    // Функция, чтобы сбрасывать ошибку, когда пользователь начинает вводить текст
    fun resetError() {
        if (_authState.value is AuthState.Error) {
            _authState.value = AuthState.Idle
        }
    }
}