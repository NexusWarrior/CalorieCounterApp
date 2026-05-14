package ru.example.caloriecounterapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun register(email: String, pass: String, confirmPass: String, terms: Boolean) {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Заполните все поля")
            return
        }
        if (pass != confirmPass) {
            _authState.value = AuthState.Error("Пароли не совпадают")
            return
        }
        if (!terms) {
            _authState.value = AuthState.Error("Примите соглашение")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) _authState.value = AuthState.Success
                else _authState.value = AuthState.Error(task.exception?.message ?: "Ошибка")
            }
    }
}