package com.example.mifibertel.data.api

import com.example.mifibertel.data.local.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * Objeto singleton que configura y proporciona la instancia de Retrofit
 * para realizar llamadas a la API
 */
//object RetrofitClient {
//    // URL base para el emulador de Android
//    private const val BASE_URL = "http://10.0.2.2:8000/"
//
//    /**
//     * Crea y configura una instancia de ApiService con las configuraciones necesarias
//     * @param tokenManager Gestor de tokens para la autenticación
//     * @return ApiService configurado para realizar llamadas a la API
//     */
//
//    fun create(tokenManager: TokenManager): ApiService {
//        // Configura el cliente HTTP con interceptores
//        val okHttpClient = OkHttpClient.Builder()
//            // Añade interceptor para logging (útil para debugging)
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//            // Añade interceptor de autenticación
//            .addInterceptor(AuthInterceptor(tokenManager))
//            .build()
//        // Construye y configura Retrofit
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL) // URL base de la API
//            .client(okHttpClient) // Cliente HTTP configurado
//            .addConverterFactory(GsonConverterFactory.create())// Conversor JSON
//            .build()
//        // Crea la implementación de la interfaz ApiService
//        return retrofit.create(ApiService::class.java)
//    }
//}