package com.example.mifibertel.data.api.model.request
/**
 * Objeto de transferencia de datos (DTO) para la petición de login.
 * Define la estructura exacta que espera la API para autenticar un usuario.
 */
data class LoginRequest(
    val username: String,
    val password: String
)