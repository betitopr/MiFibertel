package com.example.mifibertel.domain.repository

import com.example.mifibertel.domain.model.AuthToken
import com.example.mifibertel.domain.model.User
/**
 * Interfaz que define las operaciones de autenticación disponibles en la aplicación.
 *
 */
interface IAuthRepository {
    /**
     * Realiza el proceso de inicio de sesión del usuario.
     * @return Result<AuthToken> Envuelve el token de autenticación en caso de éxito
     *         o un error en caso de fallo
     */
    suspend fun login(username: String, password: String): Result<AuthToken>
    /**
     * Actualiza el token de acceso usando el token de actualización.
     *
     * @param refreshToken Token de actualización actual
     * @return Result<AuthToken> Nuevo par de tokens (acceso y actualización)
     */
    suspend fun refreshToken(refreshToken: String): Result<AuthToken>

    /**
     * Obtiene la información del usuario actualmente autenticado.
     *
     * @return Result<User> Información del usuario actual o error si no hay sesión
     */
    suspend fun getCurrentUser(): Result<User>
}