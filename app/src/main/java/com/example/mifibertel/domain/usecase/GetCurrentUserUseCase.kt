package com.example.mifibertel.domain.usecase

import com.example.mifibertel.domain.model.User
import com.example.mifibertel.domain.repository.IAuthRepository
import javax.inject.Inject

/**
 * Caso de uso para obtener la información del usuario actual
 */
class GetCurrentUserUseCase  @Inject constructor(private val authRepository: IAuthRepository) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.getCurrentUser()
    }
}