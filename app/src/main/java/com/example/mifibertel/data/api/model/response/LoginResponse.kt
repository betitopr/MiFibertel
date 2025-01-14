package com.example.mifibertel.data.api.model.response

import com.example.mifibertel.data.api.model.dto.UserDto

/**
 * Objeto de transferencia de datos (DTO) para la respuesta de login.
 * Representa la estructura exacta que devuelve la API tras un login exitoso.
 */
data class LoginResponse(
    val access: String,
    val refresh: String,
    val user: UserDto
)