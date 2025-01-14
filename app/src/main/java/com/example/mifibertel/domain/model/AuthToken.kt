package com.example.mifibertel.domain.model

/**
 * Modelo de dominio que representa los tokens de autenticación.
 * Utilizado para manejar la autenticación JWT en la aplicación.
 */
data class AuthToken(
    val access: String,
    val refresh: String
)
