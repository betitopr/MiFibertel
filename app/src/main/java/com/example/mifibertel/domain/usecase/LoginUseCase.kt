package com.example.mifibertel.domain.usecase

import com.example.mifibertel.domain.model.AuthToken
import com.example.mifibertel.domain.repository.IAuthRepository
import javax.inject.Inject

/**
 * Caso de uso que encapsula la lógica de negocio para el proceso de inicio de sesión.
 * Sigue el principio de responsabilidad única y actúa como intermediario entre la capa
 * de presentación y el repositorio.
 *
 * @param authRepository Repositorio de autenticación inyectado a través de la interfaz
 */
class LoginUseCase  @Inject constructor
    (private val authRepository: IAuthRepository) {
    /**
     * Operador invoke que permite llamar a la clase como si fuera una función.
     * Ejecuta el proceso de login delegando al repositorio.
     *
     * @param username Nombre de usuario
     * @param password Contraseña del usuario
     * @return Result<AuthToken> Resultado encapsulado del proceso de login
     */
    suspend operator fun invoke(username: String, password: String): Result<AuthToken> {
        return authRepository.login(username, password)
    }
}