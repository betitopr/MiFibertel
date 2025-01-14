package com.example.mifibertel.domain.usecase

import com.example.mifibertel.domain.model.AuthToken
import com.example.mifibertel.domain.repository.IAuthRepository
import javax.inject.Inject

/**
 * Caso de uso para refrescar el token
 */
class RefreshTokenUseCase @Inject constructor(private val authRepository: IAuthRepository) {
    suspend operator fun invoke(refreshToken: String): Result<AuthToken> {
        return authRepository.refreshToken(refreshToken)
    }
}