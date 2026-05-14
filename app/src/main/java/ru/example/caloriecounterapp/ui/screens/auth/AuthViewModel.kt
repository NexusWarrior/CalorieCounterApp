package ru.example.caloriecounterapp.ui.screens.auth

import android.app.Activity
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ErrorField {
    NAME, EMAIL, PASSWORD, CONFIRM_PASSWORD, BIRTH_DATE, TERMS, NONE
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String, val field: ErrorField = ErrorField.NONE) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun register(
        name: String,
        email: String,
        pass: String,
        confirmPass: String,
        birthDate: String,
        terms: Boolean
    ) {
        val cleanName = name.trim()
        val cleanEmail = email.replace("\\s+".toRegex(), "")
        val cleanPass = pass.trim()
        val cleanConfirmPass = confirmPass.trim()
        val cleanBirthDate = birthDate.trim()

        if (cleanName.isBlank()) {
            _authState.value = AuthState.Error("Введите ваше имя", ErrorField.NAME)
            return
        }
        if (cleanEmail.isBlank()) {
            _authState.value = AuthState.Error("Введите почту", ErrorField.EMAIL)
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
            _authState.value = AuthState.Error("Неверный формат почты", ErrorField.EMAIL)
            return
        }
        if (cleanPass.length < 6) {
            _authState.value = AuthState.Error("Пароль минимум 6 символов", ErrorField.PASSWORD)
            return
        }
        if (cleanPass != cleanConfirmPass) {
            _authState.value = AuthState.Error("Пароли не совпадают", ErrorField.CONFIRM_PASSWORD)
            return
        }
        if (cleanBirthDate.length < 8) {
            _authState.value =
                AuthState.Error("Введите полную дату рождения", ErrorField.BIRTH_DATE)
            return
        }
        if (!terms) {
            _authState.value =
                AuthState.Error("Примите пользовательское соглашение", ErrorField.TERMS)
            return
        }

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

    // Авторизация через Google
    fun signInWithGoogle(idToken: String) {
        _authState.value = AuthState.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    val (message, _) = getRussianErrorDetails(task.exception)
                    _authState.value = AuthState.Error(message, ErrorField.NONE)
                }
            }
    }

    // Авторизация через Apple (заглушка)
    fun signInWithApple(activity: Activity) {
        _authState.value = AuthState.Loading
        val provider = OAuthProvider.newBuilder("apple.com")
        provider.scopes = listOf("email", "name")

        auth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    val (message, _) = getRussianErrorDetails(task.exception)
                    _authState.value = AuthState.Error(message, ErrorField.NONE)
                }
            }
    }

    private fun getRussianErrorDetails(exception: Exception?): Pair<String, ErrorField> {
        return when (exception) {
            is FirebaseAuthWeakPasswordException -> Pair(
                "Пароль слишком простой.",
                ErrorField.PASSWORD
            )

            is FirebaseAuthInvalidCredentialsException -> Pair(
                "Неверный формат почты.",
                ErrorField.EMAIL
            )

            is FirebaseAuthUserCollisionException -> Pair(
                "Пользователь с такой почтой уже существует.",
                ErrorField.EMAIL
            )

            is FirebaseNetworkException -> Pair(
                "Ошибка сети. Проверьте подключение.",
                ErrorField.NONE
            )

            else -> Pair("Произошла неизвестная ошибка. Попробуйте позже.", ErrorField.NONE)
        }
    }

    fun resetError() {
        if (_authState.value is AuthState.Error) {
            _authState.value = AuthState.Idle
        }
    }
}