package ru.example.caloriecounterapp.ui.screens.auth

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun checkCurrentUser(): Boolean {
        return auth.currentUser != null
    }

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        birthDate: String,
        isTermsAccepted: Boolean
    ) {
        val trimmedEmail = email.trim()
        if (name.isBlank()) {
            _authState.value = AuthState.Error("Введите ваше имя", ErrorField.NAME)
            return
        }
        if (trimmedEmail.isBlank()) {
            _authState.value = AuthState.Error("Введите почту", ErrorField.EMAIL)
            return
        }
        if (password.length < 6) {
            _authState.value =
                AuthState.Error("Пароль должен быть не менее 6 символов", ErrorField.PASSWORD)
            return
        }
        if (password != confirmPassword) {
            _authState.value = AuthState.Error("Пароли не совпадают", ErrorField.CONFIRM_PASSWORD)
            return
        }
        if (birthDate.length < 8) {
            _authState.value =
                AuthState.Error("Введите корректную дату рождения", ErrorField.BIRTH_DATE)
            return
        }
        if (!isTermsAccepted) {
            _authState.value = AuthState.Error("Необходимо принять условия", ErrorField.TERMS)
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(trimmedEmail, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error(
                        task.exception?.localizedMessage ?: "Ошибка регистрации"
                    )
                }
            }
    }

    fun login(email: String, password: String) {
        val trimmedEmail = email.trim()

        if (trimmedEmail.isBlank()) {
            _authState.value = AuthState.Error(
                "Введите почту",
                ErrorField.EMAIL
            )
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            _authState.value = AuthState.Error(
                "Введите корректную почту",
                ErrorField.EMAIL
            )
            return
        }

        if (password.isBlank()) {
            _authState.value = AuthState.Error(
                "Введите пароль",
                ErrorField.PASSWORD
            )
            return
        }

        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(
            trimmedEmail,
            password
        ).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                _authState.value = AuthState.Success
                return@addOnCompleteListener
            }

            val error = when (task.exception) {

                is FirebaseAuthInvalidUserException ->
                    AuthState.Error(
                        "Пользователь не найден",
                        ErrorField.EMAIL
                    )

                is FirebaseAuthInvalidCredentialsException ->
                    AuthState.Error(
                        "Неверный пароль",
                        ErrorField.PASSWORD
                    )

                is FirebaseNetworkException ->
                    AuthState.Error(
                        "Нет подключения к интернету"
                    )

                else ->
                    AuthState.Error(
                        task.exception?.localizedMessage
                            ?: "Ошибка входа"
                    )
            }

            _authState.value = error
        }
    }

    fun signInWithGoogle(idToken: String) {
        _authState.value = AuthState.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error(
                        task.exception?.localizedMessage ?: "Ошибка входа через Google"
                    )
                }
            }
    }

    fun signInWithApple(activity: Activity) {
        _authState.value = AuthState.Loading
        val provider = OAuthProvider.newBuilder("apple.com")
        auth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error(
                        task.exception?.localizedMessage ?: "Ошибка входа через Apple"
                    )
                }
            }
    }

    fun resetError() {
        _authState.value = AuthState.Idle
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
