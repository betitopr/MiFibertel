package com.example.mifibertel.domain.model
/**
 * Modelo de dominio que representa un usuario en la aplicación.
 * Esta clase es independiente de la capa de datos y representa
 * la entidad de negocio pura.
 */
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    /** Indica si el usuario es un cliente (true) o un administrador (false) */
    val isClient: Boolean
)