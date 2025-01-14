package com.example.mifibertel.data.api

import com.example.mifibertel.data.api.model.dto.UserDto
import com.example.mifibertel.data.api.model.request.LoginRequest
import com.example.mifibertel.data.api.model.response.LoginResponse
import com.example.mifibertel.domain.model.AuthToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
/**
 * Interfaz que define los endpoints de la API para autenticación y usuario
 */
interface ApiService {
    /**
     * Endpoint para iniciar sesión
     * @param loginRequest Credenciales del usuario
     * @return Respuesta con tokens y datos del usuario
     */
    @POST("api/v1/accounts/auth/token/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    /**
     * Endpoint para renovar el token de acceso
     * @param refreshToken Token de actualización
     * @return Nuevo par de tokens
     */
    @POST("api/v1/accounts/auth/token/refresh/")
    suspend fun refreshToken(@Body refreshToken: Map<String, String>): Response<AuthToken>

    /**
     * Endpoint para obtener información del usuario actual
     * @param token Token de autenticación
     * @return Información del usuario
     */
    @GET("api/v1/accounts/users/me/")
    suspend fun getCurrentUser(): Response<UserDto>
}