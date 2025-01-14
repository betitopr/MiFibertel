package com.example.mifibertel.data.repository

import com.example.mifibertel.data.api.ApiService
import com.example.mifibertel.data.api.model.request.LoginRequest
import com.example.mifibertel.data.local.TokenManager
import com.example.mifibertel.domain.model.AuthToken
import com.example.mifibertel.domain.model.User
import com.example.mifibertel.domain.repository.IAuthRepository
import javax.inject.Inject

/**
 * Implementación del repositorio de autenticación que maneja las operaciones de auth con la API
 * y el almacenamiento local de tokens.
 *
 * @param apiService Servicio para las llamadas a la API
 * @param tokenManager Manejador para almacenar y recuperar tokens
 */
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : IAuthRepository {//Interface
    /**
     * Realiza el proceso de inicio de sesión del usuario.
     *
     * @param username Nombre de usuario
     * @param password Contraseña del usuario
     * @return Result<AuthToken> Token de autenticación o error
     */

    override suspend fun login(username: String, password: String): Result<AuthToken> {
        return try {
            // Realiza la llamada a la API
            val response = apiService.login(LoginRequest(username, password))

            if (response.isSuccessful) {
                // Si la respuesta es exitosa, procesa el body
                response.body()?.let { loginResponse ->
                    // Crea el token de autenticación
                    val authToken = AuthToken(loginResponse.access, loginResponse.refresh)
                    // Guarda el token localmente
                    tokenManager.saveToken(authToken)
                    // Retorna éxito con el token
                    Result.success(authToken)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                // Si la respuesta no es exitosa, retorna el error
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            // Captura cualquier error de red u otro tipo
            Result.failure(e)
        }
    }
    /**
     * Renueva el token de acceso usando el refresh token.
     * Si la renovación falla, se limpian los tokens almacenados.
     *
     * @param refreshToken Token de actualización para obtener un nuevo token de acceso
     * @return Result<AuthToken> Nuevo par de tokens o error
     */
    override suspend fun refreshToken(refreshToken: String): Result<AuthToken> {
        return try {
            // Realiza la petición de renovación enviando el refresh token
            val response = apiService.refreshToken(mapOf("refresh" to refreshToken))

            if (response.isSuccessful) {

                response.body()?.let { newToken ->
                    // Guarda el nuevo token en almacenamiento local
                    tokenManager.saveToken(newToken)
                    Result.success(newToken)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
//                Si falla el refresh, limpiar tokens
                tokenManager.clearTokens()
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            // También limpiar tokens en caso de error de red
            tokenManager.clearTokens()
            Result.failure(e)
        }
    }
    /**
     * Obtiene la información del usuario actualmente autenticado.
     * El token se maneja automáticamente a través del AuthInterceptor.
     *
     * @return Result<User> Información del usuario o error
     */
    override suspend fun getCurrentUser(): Result<User> {
        return try {
            // Ya no necesitamos manejar el token aquí, lo hace el interceptor
            val response = apiService.getCurrentUser()

            if (response.isSuccessful) {
                response.body()?.let { userDto ->
                    Result.success(userDto.toDomain())
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                when (response.code()) {
                    401 -> {
                        tokenManager.clearTokens()
                        Result.failure(Exception("Sesión expirada"))
                    }
                    403 -> {
                        tokenManager.clearTokens()
                        Result.failure(Exception("Acceso Prohibido"))
                    }
                    else -> Result.failure(Exception(response.errorBody()?.string()
                        ?: "Unknown error"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        private const val TAG = "AuthRepository"
    }
}