package com.example.mifibertel.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mifibertel.data.local.TokenManager
import com.example.mifibertel.domain.model.AuthToken
import com.example.mifibertel.domain.model.User
import com.example.mifibertel.domain.usecase.GetCurrentUserUseCase
import com.example.mifibertel.domain.usecase.LoginUseCase
import com.example.mifibertel.domain.usecase.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que maneja el estado de autenticación y usuario en la aplicación.
 * Utiliza casos de uso específicos para cada operación de autenticación.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val tokenManager: TokenManager // Añadimos esta dependencia
) : ViewModel() {
    // Solo el ViewModel puede modificar
    // Estado de autenticación mutable (interno)
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    // Estado de autenticación inmutable (público)
    // La UI solo puede observar
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // Solo el ViewModel puede modificar
    // Estado del usuario mutable (interno)
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    // Estado del usuario inmutable (público)
    // La UI solo puede observar
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    /**
     * Realiza el proceso de login y actualiza los estados correspondientes
     */
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            loginUseCase(username, password)
                .onSuccess { token ->
                    _authState.value = AuthState.Success(token)
                    // Obtiene automáticamente la información del usuario tras login exitoso
                    getCurrentUser()
                }
                .onFailure { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Unknown error")
                }
        }
    }

    /**
     * Actualiza el token de acceso usando el refresh token
     */
    fun refreshToken(refreshToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            refreshTokenUseCase(refreshToken)
                .onSuccess { token ->
                    _authState.value = AuthState.Success(token)
                }
                .onFailure { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Unknown error")
                }
        }
    }

    /**
     * Obtiene la información del usuario actual
     */
    fun getCurrentUser() {
        viewModelScope.launch {
            _userState.value = UserState.Loading

            getCurrentUserUseCase()
                .onSuccess { user ->
                    _userState.value = UserState.Success(user)
                }
                .onFailure { exception ->
                    _userState.value = UserState.Error(exception.message ?: "Unknown error")
                }
        }
    }
    /**
     * Funcion que se encarga de cerrar sesion
     * */

    fun logout() {
        viewModelScope.launch {
            tokenManager.clearTokens()
            _authState.value = AuthState.Idle
            _userState.value = UserState.Idle
        }
    }

}

/**
 * Estados posibles durante el proceso de autenticación
 */
sealed class AuthState {
    object Idle : AuthState()             // Estado inicial
    object Loading : AuthState()          // Procesando
    data class Success(val token: AuthToken) : AuthState()  // Éxito con token
    data class Error(val message: String) : AuthState()     // Error con mensaje
}

/**
 * Estados posibles para la información del usuario
 */
sealed class UserState {
    object Idle : UserState()             // Estado inicial
    object Loading : UserState()          // Procesando
    data class Success(val user: User) : UserState()  // Éxito con datos de usuario
    data class Error(val message: String) : UserState()     // Error con mensaje
}