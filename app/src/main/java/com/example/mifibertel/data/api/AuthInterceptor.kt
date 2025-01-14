package com.example.mifibertel.data.api

import com.example.mifibertel.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.http.POST
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager)
    : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 1. Obtiene la petición original
        val originalRequest = chain.request()

        // 2. Verifica si la petición necesita autenticación
        if (!requiresAuthentication(originalRequest)) {
            // Si no necesita auth, la envía tal cual
            return chain.proceed(originalRequest)
        }

        // 3. Si necesita auth, añade el token
        // Obtener el token de acceso
        val accessToken = tokenManager.getAccessToken()
        // Crear la petición con el token
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        // 4. Envía la petición modificada
        return chain.proceed(authenticatedRequest)
    }

    private fun requiresAuthentication(request: Request): Boolean {
        // Lista de endpoints que no requieren autenticación
        val noAuthEndpoints = listOf(
            "accounts/auth/token/", // Login
            "accounts/auth/token/refresh/"// Refresh token
        )
        // Si la URL contiene alguno de estos endpoints, no requiere auth
        return !noAuthEndpoints.any { request.url.toString().contains(it) }
    }
    companion object {
        private const val TAG = "AuthInterceptor"
    }
}