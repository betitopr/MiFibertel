package com.example.mifibertel.di

import com.example.mifibertel.data.api.ApiService
import com.example.mifibertel.data.local.TokenManager
import com.example.mifibertel.data.repository.AuthRepository
import com.example.mifibertel.data.repository.HomeRepository
import com.example.mifibertel.domain.repository.IAuthRepository
import com.example.mifibertel.domain.repository.IHomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        tokenManager: TokenManager
    ): IAuthRepository {
        return AuthRepository(apiService, tokenManager)
    }
    // Añadir este proveedor para IHomeRepository
    @Provides
    @Singleton
    fun provideHomeRepository(
        apiService: ApiService
    ): IHomeRepository {
        return HomeRepository(apiService)
    }
}