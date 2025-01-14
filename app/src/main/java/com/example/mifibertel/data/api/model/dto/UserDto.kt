package com.example.mifibertel.data.api.model.dto

import com.example.mifibertel.domain.model.User

/**
 * (DTO) que representa la estructura
 * de usuario que devuelve la API. Este modelo mapea exactamente la
 * respuesta JSON del servidor.
 */
data class UserDto(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val isClient: Boolean,
    // Campos adicionales que vienen de la API según tu serializer de Django
){
/**
 * Función de extensión para convertir UserDto a User (modelo de dominio)
 */
    fun toDomain(): User {
        return User(
            id = id,
            username = username,
            email = email,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            isClient = isClient
        )
    }
}