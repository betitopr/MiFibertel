package com.example.mifibertel.di
import com.example.mifibertel.domain.repository.IAuthRepository
import com.example.mifibertel.domain.usecase.GetCurrentUserUseCase
import com.example.mifibertel.domain.usecase.LoginUseCase
import com.example.mifibertel.domain.usecase.RefreshTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(repository: IAuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    fun provideRefreshTokenUseCase(repository: IAuthRepository): RefreshTokenUseCase {
        return RefreshTokenUseCase(repository)
    }

    @Provides
    fun provideGetCurrentUserUseCase(repository: IAuthRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(repository)
    }
}